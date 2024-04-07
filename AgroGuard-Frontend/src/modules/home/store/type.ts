export interface Post {
  id: string;
  title: string;
  content: string;
  userId: string;
  userAvatar: string;
  base64Image: string[];
  userName: string;
  updatedAt: string;
}

export interface Comment {
  id: string;
  content: string;
  avatarUrl: string;
  userId: string;
  userName: string;
  createdAt: string;
}

export interface CreatePostPayload {
  title: string;
  content: string;
  base64Imgs: string[];
}
