package com.xht.auth.auth2.server.authorization.token;

import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 自定义JWT Token属性。
 *
 * @author xht
 **/
@Slf4j
@Component
@SuppressWarnings("all")
public class JwtTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    /**
     * 自定义OAuth 2.0 Token属性。
     *
     * @param context 包含OAuth 2.0 Token属性的上下文
     */
    @Override
    public void customize(JwtEncodingContext context) {
        JwtClaimsSet.Builder claims = context.getClaims();
        OAuth2TokenType tokenType = context.getTokenType();
        Authentication principal = context.getPrincipal();
        if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType) && principal instanceof UsernamePasswordAuthenticationToken) {
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
