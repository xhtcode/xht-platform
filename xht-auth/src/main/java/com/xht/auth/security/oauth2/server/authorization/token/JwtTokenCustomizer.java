package com.xht.auth.security.oauth2.server.authorization.token;

import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class JwtTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {


    /**
     * Customize the OAuth 2.0 Token attributes.
     *
     * @param context the context containing the OAuth 2.0 Token attributes
     */
    @Override
    public void customize(JwtEncodingContext context) {
        log.info("JwtTokenCustomizer tokenType={}", context.getTokenType().getValue());
        JwtClaimsSet.Builder claims = context.getClaims();
        Authentication principal = context.getPrincipal();
        BasicUserDetails basicUserDetails = new BasicUserDetails();
        if (principal instanceof UsernamePasswordAuthenticationToken && (principal.getPrincipal() instanceof BasicUserDetails details)) {
            basicUserDetails = details;
        }
        if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
            Set<String> authorizedScopes = Optional.ofNullable(context.getAuthorization())
                    .map(OAuth2Authorization::getAuthorizedScopes)
                    .orElse(Collections.emptySet());
            addClaims(claims, TokenCustomizerIdConstant.NICK_NAME, basicUserDetails.getNickName());
            addClaims(claims, TokenCustomizerIdConstant.USER_TYPE, basicUserDetails.getUserType());
            addClaims(claims, TokenCustomizerIdConstant.USER_STATUS, basicUserDetails.getUserStatus());
            if (authorizedScopes.contains(TokenCustomizerIdConstant.ROLE)) {
                addClaims(claims, TokenCustomizerIdConstant.ROLE, basicUserDetails.getRoleCodes());
            }
            if (authorizedScopes.contains(TokenCustomizerIdConstant.MENU)) {
                addClaims(claims, TokenCustomizerIdConstant.MENU, basicUserDetails.getMenuButtonCodes());
            }
            if (authorizedScopes.contains(TokenCustomizerIdConstant.PHONE)) {
                addClaims(claims, TokenCustomizerIdConstant.PHONE, basicUserDetails.getUserPhone());
            }
        }
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            addClaims(claims, TokenCustomizerIdConstant.USER_INFO, basicUserDetails);
        }
    }

    /**
     * 向JWT声明中添加非空值
     * 当供应商提供的 value 不为 null 时，才将 key-value 对添加到声明中，避免存储 null 值
     *
     * @param <T>      声明值的类型，增强类型安全性
     * @param claims   JWT声明构建器
     * @param key      声明的key
     * @param supplier 提供声明值的供应商（通过get()方法获取值）
     */
    private <T> void addClaims(JwtClaimsSet.Builder claims, String key, T supplier) {
        Optional.ofNullable(supplier).ifPresent(value -> claims.claim(key, value));
    }

}