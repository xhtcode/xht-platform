import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useLoginStore = defineStore('login', () => {
    /**
     * 登录状态
     */
    const loginStatus = ref<boolean>(false)

    /**
     * 修改登录状态
     * @param status
     */
    function changeLoginStatus(status: boolean) {
        loginStatus.value = status
    }

    return {loginStatus, changeLoginStatus}
})