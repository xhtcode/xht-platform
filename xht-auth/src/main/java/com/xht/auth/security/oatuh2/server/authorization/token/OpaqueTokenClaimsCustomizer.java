package com.xht.auth.security.oatuh2.server.authorization.token;

import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 自定义OAuth 2.0 Token属性。
 *
 * @author xht
 **/
@Slf4j
@Component
@SuppressWarnings("all")
public class OpaqueTokenClaimsCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

    /**
     * 自定义OAuth 2.0 Token属性。
     *
     * @param context 包含OAuth 2.0 Token属性的上下文
     */
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();
        Authentication principal = context.getPrincipal();
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && principal instanceof UsernamePasswordAuthenticationToken) {
            if (principal.getDetails() instanceof BasicUserDetails details) {
                claims.claim(TokenCustomizerIdConstant.USER_ID, details.getUserId());
                claims.claim(TokenCustomizerIdConstant.USER_NAME, details.getUsername());
                claims.claim(TokenCustomizerIdConstant.LOGIN_TYPE, details.getLoginType());
                claims.claim(TokenCustomizerIdConstant.DEPT_ID, details.getDeptId());
                claims.claim(TokenCustomizerIdConstant.DEPT_NAME, details.getDeptId());
                claims.claim(TokenCustomizerIdConstant.DATA_SCOPE, details.getDeptId());
                claims.claim(TokenCustomizerIdConstant.AUTHORITIES, details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
            }
        }
    }
}
