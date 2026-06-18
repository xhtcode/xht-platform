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
     * 访问令牌前缀
     */
    String XHT_TOKEN_PREFIX = "xht.";

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

    /**
     * OAuth2 授权缓存键
     */
    String AUTHORIZATION_TOKEN_KEY_PREFIX = "oauth2:authorization:token";

    /**
     * OAuth2 授权缓存键
     */
    String AUTHORIZATION_ID_KEY_PREFIX = "oauth2:authorization:id";

    /**
     * 授权码参数名
     */
    String CODE = OAuth2ParameterNames.CODE;

    /**
     * 状态参数名
     */
    String STATE = OAuth2ParameterNames.STATE;

    /**
     * 访问令牌参数名
     */
    String ACCESS_TOKEN = OAuth2ParameterNames.ACCESS_TOKEN;

    /**
     * 刷新令牌参数名
     */
    String REFRESH_TOKEN = OAuth2ParameterNames.REFRESH_TOKEN;

    /**
     * ID 令牌类型
     */
    String ID_TOKEN = "id_token";

    /**
     * 设备码参数名
     */
    String DEVICE_CODE = OAuth2ParameterNames.DEVICE_CODE;

    /**
     * 用户码参数名
     */
    String USER_CODE = OAuth2ParameterNames.USER_CODE;


    /**
     * 码云
     */
    String GITEE = "gitee";

}
