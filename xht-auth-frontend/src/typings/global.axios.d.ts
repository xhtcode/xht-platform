import type { AxiosResponse as Response } from 'axios'

/**
 * 扩展 axios 数据返回类型，可自行扩展
 */
declare module 'axios' {
  /**
   * 扩展返回值
   */
  export interface AxiosResponse<T = any> extends Response<T> {
    success: boolean
    code: number
    data: T
    encryptData?: string
    message: string
    type?: string

    [key: string]: any
  }
}

export {}
