import { ref } from 'vue'
import { defineStore } from 'pinia'
import pInIaPersistConfig from '@/stores/pinia-persist'
import { AppInfoResponse, TokenUserInfoResponse } from '@/service/model/api.model'
import { getAppList, getTokenUserInfo } from '@/service/api/auth.api'

export const useUserStore = defineStore(
  'login',
  () => {
    const loginStatus = ref<boolean>(false)

    const userInfo = ref<TokenUserInfoResponse>()

    const appList = ref<AppInfoResponse[]>([])

    function changeLoginStatus(status: boolean) {
      loginStatus.value = status
    }

    function setUserInfo(info: TokenUserInfoResponse) {
      userInfo.value = info
    }

    async function fetchUserInfo(): Promise<boolean> {
      try {
        const res = await getTokenUserInfo()
        if (res.data) {
          userInfo.value = res.data
          return true
        }
        return false
      } catch {
        return false
      }
    }

    async function fetchAppList(): Promise<boolean> {
      try {
        const res = await getAppList()
        if (res.data) {
          appList.value = res.data
          return true
        }
        return false
      } catch {
        return false
      }
    }

    function clearUserInfo() {
      loginStatus.value = false
      userInfo.value = undefined
      appList.value = []
    }

    return {
      loginStatus,
      userInfo,
      appList,
      changeLoginStatus,
      setUserInfo,
      fetchUserInfo,
      fetchAppList,
      clearUserInfo,
    }
  },
  {
    persist: pInIaPersistConfig('sso', ['loginStatus']),
  }
)
