package com.xht.auth.constant;

import com.xht.framework.core.enums.LoginTypeEnums;
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
    AuthorizationGrantType PASSWORD = new AuthorizationGrantType(LoginTypeEnums.PASSWORD.getValue());


    /**
     * 手机号验证码
     */
    AuthorizationGrantType PHONE = new AuthorizationGrantType(LoginTypeEnums.PHONE.getValue());
}
