package com.xht.auth.constant;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

/**
 * 认证常量
 *
 * @author xht
 **/
public interface AuthorizationConstant {

    /**
     * 客户端信息缓存key前缀
     */
    String OAUTH2_CLIENT_KEY_PREFIX = "xht:oauth2:client";

    /**
     * 令牌用户信息缓存键
     */
    String TOKEN_USER_INFO_KEY = "xht:user:{}";

    /**
     * 状态令牌类型
     */
    OAuth2TokenType STATE_TOKEN_TYPE = new OAuth2TokenType(OAuth2ParameterNames.STATE);

    /**
     * Spring Security 表单登录用户名字段名
     */
    String SPRING_SECURITY_FORM_USERNAME = "username";

    /**
     * Spring Security 表单登录密码字段名
     */
    String SPRING_SECURITY_FORM_PASSWORD = "password";

    /**
     * Spring Security 表单登录验证码键字段名
     */
    String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captchaKey";

    /**
     * Spring Security 表单登录验证码值字段名
     */
    String SPRING_SECURITY_FORM_CAPTCHA_CODE = "captchaCode";

    /**
     * Spring Security 表单登录手机号码字段名
     */
    String SPRING_SECURITY_FORM_PHONE = "phone";

    /**
     * Spring Security 表单登录手机验证码字段名
     */
    String SPRING_SECURITY_FORM_PHONE_CODE = "phoneCode";

}
