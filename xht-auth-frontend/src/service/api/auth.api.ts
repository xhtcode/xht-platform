import type {AxiosPromise} from 'axios'
import request from "@/utils/request";
import {
    AppInfoResponse,
    CaptchaResponseType,
    LoginRequestType,
    RegisterRequestType,
    SendSmsRequestType,
    SmsLoginRequestType
} from "@/service/model/api.model";

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
        url: Api.GENERATE_CAPTCHA,
        method: 'post',
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
        url: Api.SSO_LOGIN,
        method: 'post',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        data,
    })
}

/**
 * 注册
 */
export const ssoRegister = (data: RegisterRequestType) => {
    return request({
        url: Api.SSO_REGISTER,
        method: 'post',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        data,
    })
}

/**
 * 发送短信验证码
 */
export const sendSmsCode = (data: SendSmsRequestType) => {
    return request({
        url: Api.SMS_SEND_CODE,
        method: 'post',
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
        url: Api.SMS_LOGIN,
        method: 'post',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        data,
    })
}

/**
 * 获取授权参数
 */
export const getConsentParameters = (data: any) => {
    return request({
        url: Api.CONFIRM_ACCESS,
        method: 'post',
        params: {...data}
    })
}

/**
 * 获取用户信息
 */
export const getTokenUserInfo = () => {
    return request({
        url: Api.TOKEN_USER_INFO,
        method: 'get',
    })
}

/**
 * 获取应用列表
 */
export const getAppList = (): AxiosPromise<AppInfoResponse[]> => {
    return request({
        url: Api.APP_LIST,
        method: 'get',
    })
}


export const getOauth2Authorize = (data: any) => {
    return request({
        url: Api.OAUTH2_AUTHORIZE,
        method: 'post',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        data,
    })
}
