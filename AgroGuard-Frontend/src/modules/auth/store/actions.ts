import { defineStore } from 'pinia'
// import { ILogin } from './types'
import { AUTH_TOKEN } from 'core/constants'
import {useState} from "./state";
import router from "router";

/**
 * 管理用户登录登出
 * **/
export const useActions = defineStore('auth.actions', () => {
  const state = useState(); // 获取 useState store 的实例
  /**
   * 传入登录信息和获得的token并进行重定向页面
   * **/
  const actLogin = async (token: string, user: any) => {
    state.login(token, user)
    // window.location.href = '/'
    await router.push('/')
  }

  /**
   * 清除token并重定向页面
   * **/
  const actLogout = () => {
    state.logout()
    window.location.href = '/login'
  }

  /**
   * 注册用户方法
   * **/
  const actSignup = () => {

  }

  return {
    actLogin,
    actLogout,
  }
})
