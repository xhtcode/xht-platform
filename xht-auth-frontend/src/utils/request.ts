import axios, { AxiosRequestConfig } from 'axios'
import qs from 'qs'
import { useMessage } from '@/hooks/use-message'

// 默认配置
const defaultConfig: AxiosRequestConfig = {
  baseURL: import.meta.env.VITE_BASE_API, // 从环境变量获取基础地址
  timeout: 50000, // 超时时间
  headers: { 'Content-Type': 'application/json;charset=utf-8' },
  withCredentials: true, // 跨域携带凭证
  paramsSerializer: {
    serialize: (params) => qs.stringify(params, { arrayFormat: 'repeat' }),
  },
}

/**
 * 创建Axios实例
 */
const service = axios.create(defaultConfig)

/**
 * 添加防缓存时间戳参数
 */
service.defaults.params = {
  _t: Date.now(), // 避免GET请求缓存
}

/**
 * 请求拦截器：处理请求头、认证信息等
 */
service.interceptors.request.use(
  (config) => {
    return config
  },
  (error) => {
    useMessage().error('请求准备失败，请稍后重试')
    console.error('请求拦截器错误:', error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response) => {
    console.log(response)
    if (response.data?.code !== 200) {
      if (response.data.code === 424) {
        window.location.href = '/sso/login?error'
        return
      }
      useMessage().error(response.data.msg || '请求失败')
      return Promise.reject(response.data || '请求失败')
    }
    return response.data
  },
  (error: any) => {
    console.log(error.response)
    if (error.response.data && error.response.data.code === 424) {
      window.location.href = '/sso/login?error'
      return
    } else {
      // 对响应错误做点什么
      if (error.message.indexOf('timeout') !== -1) {
        useMessage().error('服务器正在伸懒腰，请稍后再来~')
      } else if (error.message === 'Network Error') {
        useMessage().error('请求传递过程中超时了，就像寄快递路上堵车了~')
      } else if (error.message.indexOf('Request') !== -1) {
        useMessage().error('网络连接异常，请求未成功发送，请检查网络后重试~')
      }
    }
    return Promise.reject(error)
  }
)
export default service
