export interface ChatMessage {
  id?: string;
  chatRoomId: string;
  senderId: string;
  content: string;
  timestamp?: Date;
}

export interface ChatRoom {
  id: string;
  name: string;
  participantsId: string[];
  // 添加其他需要的属性
}

export interface MutualFollowing {
  id: string;
  username: string;
  avatar: string;
  // 添加其他需要的属性
}

export interface Contact {
  id: string;
  name: string;
  avatar: string;
  // 添加其他需要的属性
}
