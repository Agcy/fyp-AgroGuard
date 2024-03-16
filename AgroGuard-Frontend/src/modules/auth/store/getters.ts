import { defineStore } from 'pinia'

import { computed } from 'vue'
import { useState } from './state'

export const useGetters = defineStore('auth.getters', () => {
  const state = useState()
  // 自动更新是否认证的状态
  const getAuthenticationState = computed((): boolean => state.isAuthenticated)
  const getuser = computed((): any => state.user)
  return { getAuthenticationState, getuser }
})
