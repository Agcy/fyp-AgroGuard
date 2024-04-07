import {ChatMessage, ChatRoom, Contact, MutualFollowing} from "modules/chat/store/type";
import {User} from "@sentry/vue";

export interface ChatState {
  messages: ChatMessage[]; // 使用 ChatMessage 类型数组
  chatRooms: ChatRoom[];
  mutualFollowing: MutualFollowing[]; // Add this line
  contacts: Contact[];
  currentChatRoom: ChatRoom | null;
}
