export interface AuthState {
  isAuthenticated: boolean
  user?: {
    id: string;
    username: string;
    email: string;
    avatarUrl: string;
    roles: string[];
    onlineStatus: boolean;
  };
}

// export type ILogin = {
//   account: string
//   password: string
// }
