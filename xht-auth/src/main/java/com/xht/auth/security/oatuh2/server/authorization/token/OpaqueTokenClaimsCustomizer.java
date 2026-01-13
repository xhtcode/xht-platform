package com.xht.auth.security.oatuh2.server.authorization.token;

import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 自定义OAuth 2.0 Token属性。
 *
 * @author xht
 **/
@Slf4j
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
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            if (principal instanceof OAuth2ClientAuthenticationToken) {
                claims.claim(TokenCustomizerIdConstant.LOGIN_TYPE, LoginTypeEnums.CLIENT_CREDENTIALS);
            }
            if (principal instanceof UsernamePasswordAuthenticationToken && (principal.getPrincipal() instanceof BasicUserDetails details)) {
                addClaims(claims, () -> details);
            }
        }
    }

    /**
     * 向JWT声明中添加非空值
     * 当供应商提供的 value 不为 null 时，才将 key-value 对添加到声明中，避免存储 null 值
     *
     * @param <T>      声明值的类型，增强类型安全性
     * @param claims   JWT声明构建器
     * @param supplier 提供声明值的供应商（通过get()方法获取值）
     */
    private <T> void addClaims(OAuth2TokenClaimsSet.Builder claims, Supplier<T> supplier) {
        Optional.ofNullable(supplier.get()).ifPresent(value -> claims.claim(TokenCustomizerIdConstant.USER_INFO, value));
    }


}
