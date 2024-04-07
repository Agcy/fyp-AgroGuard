// src/stores/chatStore.ts
import { defineStore } from 'pinia';
import { ElNotification } from 'element-plus';
import { apiGetMessageByChatRoomId } from 'myApi/chat-api/getMessageByChatRoomId';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import Vue, { ref, watch } from 'vue';
import {useStore} from "vuex";
import {apiGetChatRoomByParticipant} from "myApi/chat-api/getChatRoomsByParticipant";

// 定义 State 和 Message 类型
interface Message {
  content: string;
  date: Date;
  self: boolean;
}

interface Session {
  [key: string]: Message[];
}

interface ChatRoom {
  chatRoomId: string;
  username: string;
  avatarUrl: string;
  [index: string]: any; // 允许Hr对象有任意属性
}

interface Friend{
  username: string;
  avatarUrl: string;
  [index: string]: any; // 允许Hr对象有任意属性
}
interface CurrentSession {
  chatRoomId: string | null;
  username: string;
  avatarUrl: string;
}
export const useChatStore = defineStore('chat', () => {
  const currentHr = ref<ChatRoom | null>(JSON.parse(window.sessionStorage.getItem("user") || 'null'));
  const sessions = ref<Session>({});
  const chatrooms = ref<ChatRoom[]>([]);
  const currentSession = ref<CurrentSession>({ chatRoomId: '', username: '', avatarUrl: '' });
  const isDot = ref<{ [key: string]: boolean }>({});
  const stomp = ref<Stomp.Client | null>(null);
  const friends = ref<Friend[]>([]);
  const store = useStore();

  function initCurrentHr(hr: ChatRoom) {
    currentHr.value = hr;
  }

  function changeCurrentSession(sessionId: string) {
    if (currentHr.value) {
      console.log("新的发送对象为：" + sessionId);
      isDot.value[currentHr.value.username + '#' + sessionId] = false;
      currentSession.value.chatRoomId = sessionId;
    }
  }

  function addMessage(msg: Message, to: string) {
    if (currentHr.value) {
      let mss = sessions.value[currentHr.value.username + '#' + to];
      if (!mss) {
        sessions.value[currentHr.value.username + '#' + to] = [];
      }
      sessions.value[currentHr.value.username + '#' + to].push(msg);
    }
  }

  function commit(title: string, type: string) {
    store.commit(title, type);
  }

  function dispatch(data: string) {
    store.dispatch(data).then(r => {});
  }

  function initData(userId: string) {
    // 直接调用其他 action
    apiGetChatRoomByParticipant(userId).then(resp => {
      if (resp) {
        chatrooms.value = resp.data;
      }
    });
  }

  function connect() {
    if (stomp.value === null) {
      stomp.value = Stomp.over(new SockJS('/ws/chat')); //todo
      stomp.value.connect({}, () => {
        console.log('连接成功');
        stomp.value?.subscribe('/chat/topic/chat.sendMessage', (msg) => {
          const receiveMsg = JSON.parse(msg.body);
          if (!currentSession.value || receiveMsg.from !== currentSession.value.username) {
            ElNotification.info({
              title: `【${receiveMsg.fromNickname}】发来一条消息`,
              message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
              position: 'bottom-right',
            });
            if (currentHr.value) {
              isDot.value[currentHr.value.username + '#' + receiveMsg.from] = true;
            }
          }
          receiveMsg.notSelf = true;
          receiveMsg.to = receiveMsg.from;
          addMessage(receiveMsg, receiveMsg.to);
        });
      }, error => {
        console.error('连接失败:', error);
        ElNotification.error({
          title: "系统消息",
          message: "WebSocket连接失败",
        });
      });
    }
  }

  // 监听 sessions 的变化并保存到 localStorage
  watch(sessions, (newSessions) => {
    localStorage.setItem('vue-chat-session', JSON.stringify(newSessions));
  }, { deep: true });

  // 其他 actions...

  return {
    currentHr,
    sessions,
    chatrooms,
    currentSession,
    isDot,
    stomp,
    commit,
    dispatch,
    initCurrentHr,
    changeCurrentSession,
    addMessage,
    initData,
    friends,
    connect,
    // 其他方法...
  };
});
