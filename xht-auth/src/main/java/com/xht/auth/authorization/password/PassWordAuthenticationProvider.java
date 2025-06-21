package com.xht.auth.authorization.password;

import com.xht.auth.authorization.AbstractAuthenticationProvider;
import com.xht.auth.constant.CustomAuthorizationGrantType;
import com.xht.auth.domain.RequestUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 密码授权类型认证提供者
 *
 * @author xht
 **/
@Slf4j
public class PassWordAuthenticationProvider extends AbstractAuthenticationProvider {


    public PassWordAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(CustomAuthorizationGrantType.PASSWORD, authenticationManager, authorizationService, tokenGenerator);
    }

    /**
     * 获取认证过的用户信息
     *
     * @param requestUserBO 用户请求信息
     * @return 认证信息
     */
    @Override
    protected Authentication doAuthenticate(RequestUserBO requestUserBO) throws AuthenticationException {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(requestUserBO.getUserName(), requestUserBO.getPassWord());
        return authenticationManager.authenticate(unauthenticated);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return PassWordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
