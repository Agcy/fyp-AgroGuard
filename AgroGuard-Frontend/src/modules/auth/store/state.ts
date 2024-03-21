import { defineStore } from 'pinia'
import { AuthState } from './types'
import { AUTH_TOKEN } from 'core/constants'


/**
 * 实时更新token是否被清除
 * **/
export const useState = defineStore({
  id: 'auth.state',
  state: (): AuthState => ({
    isAuthenticated: false, // 初始状态
    user: undefined, // 初始用户状态
  }),
  actions: {
    setUser(user: any) {
      this.user = user;
      this.isAuthenticated = !!user;
    },
    clearUser() {
      this.user = undefined;
      this.isAuthenticated = false;
    },
    login(token: string, user: any) {
      localStorage.setItem(AUTH_TOKEN, token); // 更新localStorage中的token
      this.setUser(user); // 更新用户状态和认证状态
      console.log(localStorage)
    },
    logout() {
      localStorage.removeItem(AUTH_TOKEN); // 清除localStorage中的token
      this.clearUser(); // 清除用户信息和认证状态
    },
    signup(){

    }
  },
})
