package com.xht.auth.security.oatuh2.server.authorization.password;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.constant.CustomAuthorizationGrantType;
import com.xht.auth.security.oatuh2.server.authorization.AbstractAuthenticationProvider;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.domain.RequestUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
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

    private final BasicUserDetailsService basicUserDetailsService;

    private final ICaptchaService iCaptchaService;

    public PassWordAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                          OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                          BasicUserDetailsService basicUserDetailsService,
                                          ICaptchaService iCaptchaService) {
        super(authorizationService, tokenGenerator);
        this.basicUserDetailsService = basicUserDetailsService;
        this.iCaptchaService = iCaptchaService;
    }


    /**
     * 获取认证过的用户信息
     *
     * @param requestUserBO 用户请求信息
     * @param authentication 认证信息
     * @return 认证信息
     */
    @Override
    protected BasicUserDetails getAuthenticatedPrincipal(RequestUserBO requestUserBO, Authentication authentication) {
        requestUserBO.checkUserName();
        requestUserBO.checkPassWord();
        iCaptchaService.checkCaptcha(requestUserBO.generateCaptchaKey(), requestUserBO.getCaptcha());
        return basicUserDetailsService.loadUserByUsername(requestUserBO.getUserName(), LoginTypeEnums.PASSWORD);
    }

    /**
     * 获取认证类型.
     *
     * @return 认证类型
     */
    @Override
    protected AuthorizationGrantType getGrantType() {
        return CustomAuthorizationGrantType.PASSWORD;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return PassWordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
