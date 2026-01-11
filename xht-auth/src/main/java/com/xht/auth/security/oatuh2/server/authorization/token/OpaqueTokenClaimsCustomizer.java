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

import static com.xht.framework.security.constant.TokenCustomizerIdConstant.*;

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
                addClaims(claims, USER_ID, details::getUserId);
                addClaims(claims, USER_TYPE, details::getUserType);
                addClaims(claims, USER_NAME, details::getUsername);
                addClaims(claims, NICK_NAME, details::getNickName);
                addClaims(claims, USER_STATUS, details::getUserStatus);
                addClaims(claims, USER_PHONE, details::getUserPhone);
                addClaims(claims, DEPT_ID, details::getDeptId);
                addClaims(claims, DEPT_NAME, details::getDeptName);
                addClaims(claims, POST_ID, details::getPostId);
                addClaims(claims, ROLE_CODES, details::getRoleCodes);
                addClaims(claims, MENU_BUTTON_CODE, details::getMenuButtonCodes);
                addClaims(claims, DATA_SCOPE, details::getDataScope);
                addClaims(claims, LOGIN_TYPE, details::getLoginType);
            }
        }
    }

    /**
     * 向JWT声明中添加非空值
     * 当供应商提供的 value 不为 null 时，才将 key-value 对添加到声明中，避免存储 null 值
     *
     * @param claims   JWT声明构建器
     * @param key      声明的键
     * @param supplier 提供声明值的供应商（通过get()方法获取值）
     * @param <T>      声明值的类型，增强类型安全性
     */
    private <T> void addClaims(OAuth2TokenClaimsSet.Builder claims, String key, Supplier<T> supplier) {
        Optional.ofNullable(supplier.get()).ifPresent(value -> claims.claim(key, value));
    }


}
