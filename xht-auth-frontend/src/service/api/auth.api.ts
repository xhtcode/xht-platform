import type { AxiosPromise } from 'axios'
import request from '@/utils/request'
import {
  AppInfoResponse,
  CaptchaResponseType,
  LoginRequestType,
  RegisterRequestType,
  SendSmsRequestType,
  SmsLoginRequestType,
} from '@/service/model/api.model'

const baseURL = '/api'
enum Api {
  GENERATE_CAPTCHA = '/login/captcha',
  SSO_LOGIN = '/sso/unLogin',
  OAUTH2_AUTHORIZE = '/oauth2/authorize',
  SSO_REGISTER = '/sso/register',
  SMS_SEND_CODE = '/sms/sendCode',
  SMS_LOGIN = '/sms/login',
  TOKEN_USER_INFO = '/token/info',
  CONFIRM_ACCESS = '/oauth2/authorization/consent/info',
  APP_LIST = '/app/list',
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
 * 获取授权参数
 */
export const getConsentParameters = (data: any) => {
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
 * 获取应用列表
 */
export const getAppList = (): AxiosPromise<AppInfoResponse[]> => {
  return request({
    method: 'get',
    url: Api.APP_LIST,
  })
}

export const getOauth2Authorize = (data: any) => {
  return request({
    method: 'post',
    url: Api.OAUTH2_AUTHORIZE,
    baseURL: baseURL,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data,
  })
}
export const getUserInfo = (): AxiosPromise<AppInfoResponse[]> => {
  return request({
    method: 'get',
    url: '/userinfo',
    headers: {
      Authorization:
        'Bearer ' +
        'eyJraWQiOiI5M2Q4MTFiMS01ZmEyLTQwNTQtYTMzMi1mMTQxZjU5NzdiODkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6Im9pZGMtY2xpZW50IiwiYXpwIjoib2lkYy1jbGllbnQiLCJhdXRoX3RpbWUiOjE3NzczNjAzNDEsImlzcyI6Imh0dHA6Ly94aHRpYW0uY29tOjkwMDAiLCJleHAiOjE3NzczNjIzMDcsImlhdCI6MTc3NzM2MDUwNywibm9uY2UiOiJ1NEdwR0UyOHBNNHVHR0YwQ0FVdjRjRExaVlhiRWFwUFJZTE15Z0pnTndJIiwianRpIjoiMjg5NTIxMzMtOWE3Ni00YWM5LWE1ZGItZDY5ZmM0MmNhMWZlIiwic2lkIjoiNzZOWTVldTEtOVoteXBSMUZPX3IzNDFGWnFkWUFENHJTNm82S2NZWE52ZyJ9.Q5yO6gT2Uw_fZEVsjdvGap-BP6D-1ikYfPiN5iz2hN5Hua0TzYD_fBmLcsk_z-6BSHHRjiCp1fgR1RYgE-q_MtX6euKsVnBgO3Yv1cU1GnEtpHVDLWW_CfD6Sz17RYKMG4YR_3nNISFusTItyrxNP-6LaV0o_lVfvOwV5neixnvUj_z3JVJkqX361jet3kD6CcnfWhEvGFUV6ZQk3JqDeoODZYl22ZeLcWcMK96_6uLwtJVy_s7Bamc24Daj_U3l3JwPBxOVVEoo40UDc-ROzNsptAD7WgwRuac8tH4drnsw6AZQIMtJ_igWRS0R9OXsWc4Qo7z1Ek-OV4-ykhDy5g',
    },
  })
}
