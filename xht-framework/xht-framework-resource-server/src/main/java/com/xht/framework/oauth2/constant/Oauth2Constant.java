package com.xht.framework.oauth2.constant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 自定应授权类型
 *
 * @author xht
 **/
@SuppressWarnings("all")
public interface Oauth2Constant {

    /**
     * 授权信息缓存key前缀
     */
    String AUTHORIZATION_KEY_PREFIX = "authorization";

    /**
     * 授权确认信息缓存key前缀
     */
    String AUTHORIZATION_CONSENT_KEY_PREFIX = "authorizationConsent";

    /**
     * 密码模式
     */
    AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");


    /**
     * 手机号验证码
     */
    AuthorizationGrantType PHONE_SMS = new AuthorizationGrantType("phone_sms");

}
