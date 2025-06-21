package com.xht.auth.authorization;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

/**
 * 描述 ： 抽象  令牌
 *
 * @author 小糊涂
 **/
@Getter
public abstract class AbstractAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {


    /**
     * 本次登录申请的scope
     */
    private final Set<String> scopes;
    /**
     * 客户端认证信息
     */
    private final Authentication clientPrincipal;

    /**
     * 当前请求的参数
     */
    private final Map<String, Object> additionalParameters;

    /**
     * 认证方式
     */
    private final AuthorizationGrantType authorizationGrantType;

    public AbstractAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                       Authentication clientPrincipal,
                                       @Nullable Set<String> scopes,
                                       @Nullable Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);
        this.scopes = scopes;
        this.clientPrincipal = clientPrincipal;
        this.additionalParameters = additionalParameters;
        this.authorizationGrantType = authorizationGrantType;
    }

}