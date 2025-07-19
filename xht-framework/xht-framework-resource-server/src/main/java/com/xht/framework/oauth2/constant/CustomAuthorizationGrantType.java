package com.xht.framework.oauth2.constant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 自定应授权类型
 *
 * @author xht
 **/
@SuppressWarnings("all")
public interface CustomAuthorizationGrantType {


    /**
     * 密码模式
     */
    AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");


    /**
     * 手机号验证码
     */
    AuthorizationGrantType PHONE_SMS = new AuthorizationGrantType("phone_sms");
}
