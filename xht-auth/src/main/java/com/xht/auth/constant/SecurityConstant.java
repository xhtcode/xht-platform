package com.xht.auth.constant;

/**
 * 描述 ： security 常量
 *
 * @author xht
 **/
public interface SecurityConstant {

    /**
     * oauth2授权类型类型
     */
    String REQUEST_OAUTH2_GRANT_TYPE = "grant_type";

    /**
     * 请求的`账号`name值
     */
    String REQUEST_USERNAME = "username";

    /**
     * 请求的`密码`name值
     */
    String REQUEST_PASSWORD = "password";

    /**
     * 登录方式
     */
    String REQUEST_LOGIN_TYPE = "login_type";

    /**
     * 验证码
     */
    String REQUEST_CAPTCHA_CODE = "captcha_code";


    /**
     * 验证码key
     */
    String REQUEST_CAPTCHA_CODE_KEY = "captchaKey";


    /**
     * 验证码 redis key 前缀
     */
    String REDIS_CAPTCHA_CODE_KEY_PREFIX = "captcha:code:";

}
