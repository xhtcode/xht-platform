import { ref } from 'vue'
import { defineStore } from 'pinia'
import pInIaPersistConfig from '@/stores/pinia-persist'
import type { TokenUserInfoResponse } from '@/service/model/api.model'
import { getTokenUserInfo, ssoLogout } from '@/service/api/auth.api'

export const useUserStore = defineStore(
  'login',
  () => {
    /**
     * 登录状态
     */
    const loginStatus = ref<boolean>(false)

    /**
     * 用户信息
     */
    const userInfo = ref<TokenUserInfoResponse>()

    /**
     * 修改登录状态
     *
     * @param status 登录状态
     */
    function changeLoginStatus(status: boolean) {
      loginStatus.value = status
    }

    /**
     * 获取用户信息
     */
    async function fetchUserInfo(): Promise<boolean> {
      return new Promise((resolve, reject) => {
        getTokenUserInfo()
          .then((res) => {
            userInfo.value = res.data
            resolve(true)
          })
          .catch(() => {
            reject(false)
          })
      })
    }

    /**
     * 退出登录
     */
    function clearUserInfo() {
      return new Promise((resolve, reject) => {
        ssoLogout()
          .then((res) => {
            loginStatus.value = false
            userInfo.value = undefined
            resolve(res)
          })
          .catch((err) => {
            reject(err)
          })
      })
    }

    return {
      loginStatus,
      userInfo,
      changeLoginStatus,
      fetchUserInfo,
      clearUserInfo,
    }
  },
  {
    persist: pInIaPersistConfig('sso', ['loginStatus']),
  }
)
