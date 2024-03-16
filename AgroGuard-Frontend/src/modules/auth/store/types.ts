export interface AuthState {
  isAuthenticated: boolean
  user?: {
    id: string;
    username: string;
    email: string;
    roles: string[];
  };
}

// export type ILogin = {
//   account: string
//   password: string
// }
