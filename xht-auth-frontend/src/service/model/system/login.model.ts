/**
 * 账号密码登录参数类型
 */
export interface LoginPassWordFromModel {
    username: string // 账号
    password: string // 密码
    rememberMe: boolean // 记住我
    captchaKey: string // 验证码key
    captchaCode: string // 验证码
}

/**
 * 登录手机号参数类型
 */
export interface LoginPhoneFromModel {
    phone: string // 手机号
    phoneCode: string // 验证码
    rememberMe: boolean // 记住我
    captchaKey?: string // 验证码key
    captchaCode: string // 验证码
}
