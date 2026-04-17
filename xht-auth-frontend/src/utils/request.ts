import axios from 'axios'
import {useMessage} from "@/hooks/use-message";

const api = axios.create({
    baseURL: '/api',
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    },
    timeout: 10000
})

api.interceptors.request.use(
    (config) => {
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

api.interceptors.response.use(
    (response) => {
        if (response.data?.code !== 200) {
            useMessage().error(response.data.msg || '请求失败')
            return Promise.reject(response.data.msg || '请求失败')
        }
        return response.data
    },
    (error) => {
        return Promise.reject(error)
    }
)

export const authApi = {
    login: (username: string, password: string, rememberMe: boolean) => api.request({
        url: '/sso/unLogin',
        method: 'post',
        headers: {
            // 表单登录
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: {username, password,rememberMe}
    }),
    testApi: () => api.request({
        url: '/token/info',
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'get'
    }),
    getConsentParameters: (data: any) => api.request({
        url: '/sso/confirm_access',
        method: 'get',
        params: {
            ...data
        }
    })
}

export default api
