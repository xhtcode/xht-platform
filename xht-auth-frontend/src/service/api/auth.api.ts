import type { AxiosPromise } from 'axios'
import request from '@/utils/request'
import type { CaptchaResponseType, LoginRequestType, RegisterRequestType, SendSmsRequestType, SmsLoginRequestType } from '@/service/model/api.model'

const baseURL = '/api'

enum Api {
  GENERATE_CAPTCHA = '/sso/login/captcha',
  SSO_LOGIN = '/sso/unLogin',
  OAUTH2_AUTHORIZE = '/oauth2/authorize',
  SSO_REGISTER = '/sso/register',
  SMS_SEND_CODE = '/sms/sendCode',
  SMS_LOGIN = '/sms/login',
  TOKEN_USER_INFO = '/token/info',
  CONFIRM_ACCESS = '/oauth2/authorization/consent/info',
}

/**
 * 获取验证码信息
 */
export const generateCaptcha = (captchaKey: string): AxiosPromise<CaptchaResponseType> => {
  return request({
    method: 'post',
    url: Api.GENERATE_CAPTCHA,
    baseURL: baseURL,
    headers: {
      skipToken: true,
    },
    params: {
      captcha_key: captchaKey,
    },
  })
}

/**
 * 登录
 */
export const ssoLogin = (data: LoginRequestType) => {
  return request({
    method: 'post',
    url: Api.SSO_LOGIN,
    baseURL: baseURL,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data,
  })
}

/**
 * 注册
 */
export const ssoRegister = (data: RegisterRequestType) => {
  return request({
    method: 'post',
    url: Api.SSO_REGISTER,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data,
  })
}

/**
 * 发送短信验证码
 */
export const sendSmsCode = (data: SendSmsRequestType) => {
  return request({
    method: 'post',
    url: Api.SMS_SEND_CODE,
    headers: {
      skipToken: true,
    },
    data,
  })
}

/**
 * 短信登录
 */
export const smsLogin = (data: SmsLoginRequestType) => {
  return request({
    method: 'post',
    url: Api.SMS_LOGIN,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data,
  })
}

/**
 * 退出登录 /sso/lout
 */
export const ssoLogout = () => {
  return request({
    method: 'post',
    url: '/sso/logout',
    baseURL: baseURL,
  })
}

/**
 * 获取授权参数
 */
export const findConsentByParameters = (data: any) => {
  return request({
    method: 'post',
    baseURL: baseURL,
    url: Api.CONFIRM_ACCESS,
    params: { ...data },
  })
}

/**
 * 获取用户信息
 */
export const getTokenUserInfo = () => {
  return request({
    method: 'get',
    baseURL: baseURL,
    url: Api.TOKEN_USER_INFO,
  })
}

/**
 * 提交授权
 * @param data 授权参数
 */
export const submitOauth2Authorize = (data: FormData) => {
  return request({
    method: 'post',
    url: Api.OAUTH2_AUTHORIZE,
    baseURL: baseURL,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data,
  })
}
