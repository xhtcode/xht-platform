package com.xht.framework.security.constant;

/**
 * 描述 ： security 常量
 *
 * @author xht
 **/
public interface SecurityConstant {

    /**
     * 验证码过期时间
     */
    long CAPTCHA_EXPIRE_TIME = 60 * 2;

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
     * 验证码
     */
    String REQUEST_CAPTCHA_CODE = "captcha_code";


    /**
     * 验证码key
     */
    String REQUEST_CAPTCHA_CODE_KEY = "captcha_key";


    /**
     * 验证码 redis key 前缀
     */
    String REDIS_CAPTCHA_CODE_KEY_PREFIX = "captcha:code:";

    /**
     * 手机验证码 redis key 前缀
     */
    String REDIS_PHONE_CODE_KEY_PREFIX = "captcha:phone";

    /**
     * 用户手机验证码 redis key 前缀
     */
    String USER_REGISTER_PHONE_KEY_PREFIX = "user:register:phone";

    /**
     * 角色前缀
     */
    String ROLE_PREFIX = "ROLE_";

    /**
     * 密码盐值链接符号
     */
    String PASSWORD_JOIN_SALT = "#";
}
