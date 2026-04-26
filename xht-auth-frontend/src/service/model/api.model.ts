/**
 * 验证码返回类型
 */
export interface CaptchaResponseType {
    key: string
    code: string
}

/**
 * 账号密码登录参数类型
 */
export interface LoginRequestType {
    username: string // 账号
    password: string // 密码
    rememberMe: boolean // 记住我
    captchaKey: string // 验证码key
    captchaCode: string // 验证码
}

/**
 * 注册参数类型
 */
export interface RegisterRequestType {
    username: string // 用户名
    password: string // 密码
    nickName: string // 昵称
}

/**
 * 短信登录参数类型
 */
export interface SmsLoginRequestType {
    phone: string // 手机号
    smsCode: string // 短信验证码
}

/**
 * 发送短信参数类型
 */
export interface SendSmsRequestType {
    phone: string // 手机号
}

/**
 * token 用户信息
 */
export interface TokenUserInfoResponse {
    userType: string;
    userName: string;
    nickName: string;
    userStatus: string | number;
    userPhone: string;
    userAvatar: string;
    deptId: number;
    deptName: string;
    registerDate: string;
}

/**
 * 应用信息
 */
export interface AppInfoResponse {
    appId: string;
    appName: string;
    appLogo: string;
    appUrl: string;
    appDescription: string;
    appStatus: string | number;
}