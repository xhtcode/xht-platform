package com.xht.auth.constant;

import com.xht.framework.common.enums.LoginTypeEnum;
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
    AuthorizationGrantType PASSWORD = new AuthorizationGrantType(LoginTypeEnum.PASSWORD.getValue());


    /**
     * 手机号验证码
     */
    AuthorizationGrantType PHONE = new AuthorizationGrantType(LoginTypeEnum.PHONE.getValue());

}
