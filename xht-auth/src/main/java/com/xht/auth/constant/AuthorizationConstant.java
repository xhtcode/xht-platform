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
     * 令牌用户信息缓存键
     */
    String TOKEN_USER_INFO_KEY = "xht:user:{}";

    /**
     * 状态令牌类型
     */
    OAuth2TokenType STATE_TOKEN_TYPE = new OAuth2TokenType(OAuth2ParameterNames.STATE);

}
