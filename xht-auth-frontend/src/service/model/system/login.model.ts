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

/**
 * 二维码状态类型
 * waiting: 等待用户扫码
 * scanned: 用户已扫码
 * confirmed: 用户授权成功
 * expired: 二维码已过期
 */
export type QrCodeStatusType = 'waiting' | 'scanned' | 'confirmed' | 'expired'
