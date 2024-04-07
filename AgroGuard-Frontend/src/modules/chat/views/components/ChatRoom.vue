<template>
  <div class="chat-app">
    <!-- 聊天区域 -->
    <div class="chat-room">
      <!-- 消息显示区 -->
      <div class="messages">
        <div v-for="message in messages" :key="message.id" class="message" :class="{'my-message': message.isMine}">
          <el-avatar :src="message.avatar"/>
          <div class="content">{{ message.content }}</div>
        </div>
      </div>
      <!-- 输入区域 -->
      <div class="input-area">
        <el-input
          v-model="newMessage"
          class="input-field"
          placeholder="Type a message..."
          @keyup.enter="sendMessage"
        ></el-input>
        <el-button type="success" icon="el-icon-send" circle @click="sendMessage"></el-button>
      </div>
    </div>

    <!-- 侧边栏区域 -->
    <el-tabs v-model="activeTab" class="sidebar-tabs">
      <!-- 最近聊天 -->
      <el-tab-pane label="最近聊天" name="recent">
        <div class="chat-list">
          <el-menu
            :default-active="defaultChatRoom"
            class="chat-room-menu"
            @select="handleSelectChatRoom">
            <el-menu-item
              v-for="chatRoom in chatStore.chatRooms"
              :key="chatRoom.id"
              :index="chatRoom.id">
              <el-avatar :src="null"/>
              {{ chatRoom.name }}
            </el-menu-item>
          </el-menu>

          <!-- 提供一个最小高度，即使内容为空也能显示 -->
          <div v-if="chatStore.chatRooms.length === 0" class="empty-chat">没有最近的聊天</div>
        </div>
      </el-tab-pane>

      <!-- 联系人列表 -->
      <el-tab-pane label="联系人" name="contacts">
        <div class="contact-list">
          <el-button
            v-for="contact in chatStore.mutualFollowing"
            :key="contact.id"
            type="text"
            class="contact-item"
            @click="createChatRoomWithContact(contact)"
          >
            <el-avatar :src="contact.avatar" class="contact-avatar"/>
            <span>{{ contact.username }}</span>
          </el-button>
          <div v-if="chatStore.mutualFollowing.length === 0" class="empty-contact">没有联系人</div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>


<script lang="ts">
import {defineComponent, ref, onMounted, watch} from 'vue';
import {useChatStore} from '../../store/store'; // Adjust the path according to your project structure
// import { sendMessageApi, getRecentChatsApi, getContactsApi } from '@/api/chat'; // Dummy API functions, replace with your actual API calls
import {useState} from 'modules/auth/store/state'
import {ChatRoom} from "modules/chat/store/type";
import io from 'socket.io-client';
import {AUTH_TOKEN} from "core/constants";

export default defineComponent({
  name: 'ChatRoom',
  setup() {
    const chatStore = useChatStore();
    const state = useState()
    const newMessage = ref('');
    const messages = ref([]); // Start with an empty array
    const drawerVisible = ref(true);
    const activeTab = ref('recent');
    const recentChats = ref([]);
    const contacts = ref([]);
    const muFollows = ref([])
    const defaultChatRoom = ref(chatStore.chatRooms[0]?.id || '');
    let websocket: WebSocket;
    let reconnectInterval = 5000; // 重连间隔时间，例如5秒
    watch(() => chatStore.messages, (newMessages) => {
      messages.value = newMessages;
    }, {deep: true});
    const user = state.user;
    const handleTabClick = (tab: { name: any; }) => {
      console.log(tab.name);
      // 你可以在这里添加更多的逻辑，如根据选择的标签加载不同的数据
    };
    // 选择聊天室并加载消息
    const selectChatRoom = async (chatRoom: ChatRoom) => {
      await chatStore.fetchChatRooms(chatRoom);
      messages.value = chatStore.messages; // 确保组件中的messages更新
    };

    // 当用户选择不同聊天室时的处理逻辑
    const handleSelectChatRoom = (chatRoomId: string) => {
      // 根据选中的聊天室ID更新当前聊天室
      chatStore.fetchChatRoomById(chatRoomId);

      // 可以在这里调用函数来加载选中聊天室的聊天记录等
    };
    const createChatRoomWithContact = async (contact: { id: string | string[]; }) => {
      // 检查是否已存在聊天室，如果不存在，则创建
      await chatStore.createChatRoom(user?.id as string, [user?.id as string, contact.id as string]);

      // 切换视图到最近聊天列表
      activeTab.value = 'recent';

      // 可以在这里发送一个初始的消息到新创建的聊天室
      const initialMessage = '你们现在可以开始聊天了！';
      chatStore.sendMessage(initialMessage, contact.id);
    };
    let token: any;
    const onOpen = () => {
      console.log('WebSocket连接成功，状态码：', websocket.readyState)
    };
    const onMessage = (event: any) => {
      console.log('WebSocket收到消息：', event.data);
      Notification['info']({
        message: '收到消息',
        description: event.data,
      });
    };
    const onError = () => {
      console.log('WebSocket连接错误，状态码：', websocket.readyState)
    };
    const onClose = () => {
      console.log('WebSocket连接关闭，状态码：', websocket.readyState)
    };
    const initWebSocket = () => {
      // 连接成功
      websocket.onopen = onOpen;
      // 收到消息的回调
      websocket.onmessage = onMessage;
      // 连接错误
      websocket.onerror = onError;
      // 连接关闭的回调
      websocket.onclose = onClose;
    };

    onMounted(async () => {
      // Initialize WebSocket connection
      if ('WebSocket' in window) {
        //这里Tool是我写的一个工具类的一个js，websocket的token生成就写在里面
        token = localStorage.getItem(AUTH_TOKEN);
        // 连接地址：ws://127.0.0.1:8880/ws/xxx
        websocket = new WebSocket(`ws://127.0.0.1:8081/chat?${token}`);
        initWebSocket()

        // 关闭
        // websocket.close();
      } else {
        alert('当前浏览器 不支持')
      }
      if (chatStore.currentChatRoom) {
        await chatStore.fetchChatRooms(chatStore.currentChatRoom);
      }
      await chatStore.fetchMutualFollowing(user?.id as string); // 应由实际用户ID替换
      await chatStore.fetchAllChatRooms(user?.id as string);
    });

    // Function to send a message
    const sendMessage = () => {
      if (newMessage.value.trim() && websocket && websocket.readyState === WebSocket.OPEN) {
        const messageToSend = JSON.stringify({
          content: newMessage.value,
          // 添加其他需要的消息字段
        });
        websocket.send(messageToSend);
        newMessage.value = ''; // 发送后清空输入
      } else {
        console.error("WebSocket is not open. Message not sent.");
      }
    };

    // Ensure to close the WebSocket connection when the component unmounts
    window.onbeforeunload = () => {
      if (websocket) {
        websocket.close();
      }
    };


    return {
      handleTabClick,
      createChatRoomWithContact,
      defaultChatRoom,
      handleSelectChatRoom,
      newMessage,
      chatStore,
      messages,
      drawerVisible,
      activeTab,
      recentChats,
      contacts,
      selectChatRoom,
      sendMessage,
      muFollows
    };
  },
});
</script>

<style scoped>
.contact-list .contact-item {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.contact-list .contact-item:hover {
  background-color: #f5f5f5; /* 鼠标悬停时的背景色 */
}

.contact-avatar {
  margin-right: 10px;
}
.chat-app {
  display: flex;
  height: 100vh;
}

.chat-list, .contact-list {
  min-height: 200px; /* 或根据你的设计调整 */
}

.empty-chat, .empty-contact {
  color: #ccc;
  text-align: center;
  padding: 20px;
}

.chat-room {
  display: flex;
  flex-direction: column;
  height: 100vh; /* 使用视口高度使聊天室高度填满屏幕 */
  width: 100%; /* 使用视口宽度使聊天室宽度填满屏幕 */
  margin: 0; /* 移除外边距 */
  background-color: #f0f2f5;
  overflow: hidden; /* 防止内容溢出时出现滚动条 */
  flex-grow: 1; /* 使聊天室占据剩余空间 */
}

.messages {
  flex-grow: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.message {
  display: flex;
  align-items: center;
  gap: 10px;
}

.my-message {
  justify-content: flex-end;
}

.input-area {
  display: flex;
  padding: 10px;
  background-color: #fff;
}

.input-field {
  flex-grow: 1;
  margin-right: 10px;
}
</style>
