export interface AuthState {
  isAuthenticated: boolean
  user?: {
    id: string;
    username: string;
    email: string;
    occupation: string;
    avatarUrl: string;
    likedPosts: string[];
    following: string[];
    followers: string[];
    roles: string[];
    onlineStatus: boolean;
  };
}

// export type ILogin = {
//   account: string
//   password: string
// }
