import {useUserStore} from '@/stores/modules/user.store'
import {storeToRefs} from 'pinia'
import {computed, ref} from 'vue'

/**
 * 登录状态检查
 */
export function useCheckLoginHooks() {
    const userStore = useUserStore()

    const {loginStatus, userInfo} = storeToRefs(userStore)
    const isLogin = computed<boolean>(() => !!(loginStatus.value && userInfo.value))

    /**
     * 检查登录状态
     */
    const checkLoginStatus = () => {
        return new Promise<void>((resolve, reject) => {
            if (loginStatus.value && userInfo.value) {
                resolve()
                return;
            }
            userStore
                .fetchUserInfo()
                .then((res) => {
                    if (res) {
                        userStore.changeLoginStatus(true)
                        resolve()
                    } else {
                        reject()
                    }
                })
                .catch(() => {
                    reject()
                })
        })
    }

    return {
        isLogin: isLogin.value,
        checkLoginStatus,
    }
}
