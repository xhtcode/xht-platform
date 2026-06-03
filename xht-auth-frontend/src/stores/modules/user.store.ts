import {computed, ref} from 'vue'
import {defineStore} from 'pinia'
import pInIaPersistConfig from '@/stores/pinia-persist'
import {AppInfoResponse, TokenUserInfoResponse} from '@/service/model/api.model'
import {getAppList, getTokenUserInfo, ssoLogout} from '@/service/api/auth.api'

export const useUserStore = defineStore(
    'login',
    () => {
        const loginStatus = ref<boolean>(false)

        const userInfo = ref<TokenUserInfoResponse>()

        const appList = ref<AppInfoResponse[]>([])
        const skipCheck = ref<boolean>(false)

        function changeLoginStatus(status: boolean) {
            loginStatus.value = status
        }

        function setUserInfo(info: TokenUserInfoResponse) {
            userInfo.value = info
        }

        async function fetchUserInfo(): Promise<boolean> {
            return new Promise((resolve, reject) => {
                getTokenUserInfo().then((res) => {
                    userInfo.value = res.data
                    resolve(true)
                }).catch(() => {
                    reject(false)
                })
            })
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
            return new Promise((resolve, reject) => {
                ssoLogout()
                    .then((res) => {
                        console.log(res)
                        loginStatus.value = false
                        userInfo.value = undefined
                        appList.value = []
                        resolve(res)
                    })
                    .catch((err) => {
                        reject(err)
                    })
            })
        }

        return {
            skipCheck,
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
