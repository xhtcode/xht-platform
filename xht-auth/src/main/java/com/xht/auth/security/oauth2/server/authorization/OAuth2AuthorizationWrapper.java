package com.xht.auth.security.oauth2.server.authorization;

import com.xht.auth.constant.AuthorizationConstant;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.utils.crypto.MD5Utils;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 描述：oauth2 server 授权信息 包装器
 *
 * @author xht
 **/
public final class OAuth2AuthorizationWrapper implements Serializable {

    private static final long DEFAULT_TIMEOUT = 5 * 60;

    private final OAuth2Authorization oAuth2Authorization;

    private long maxTimeout;

    /**
     * 紧凑构造函数，校验授权信息不为空
     *
     * @param oAuth2Authorization OAuth2 授权信息
     */
    private OAuth2AuthorizationWrapper(OAuth2Authorization oAuth2Authorization) {
        Assert.notNull(oAuth2Authorization, "oAuth2Authorization cannot be null");
        this.oAuth2Authorization = oAuth2Authorization;
    }

    public static OAuth2AuthorizationWrapper wrap(OAuth2Authorization oAuth2Authorization) {
        return new OAuth2AuthorizationWrapper(oAuth2Authorization);
    }

    /**
     * 获取访问令牌（Access Token）并执行回调
     * <p>若授权信息中包含访问令牌，则将其与过期时间传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为访问令牌及其过期时间
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper accessToken(Consumer<AuthorizationCacheInfo> consumer) {
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = oAuth2Authorization.getToken(OAuth2AccessToken.class);
        if (Objects.nonNull(accessToken)) {
            OAuth2AccessToken token = accessToken.getToken();
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.ACCESS_TOKEN, token.getTokenValue(), token.getExpiresAt());
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 获取刷新令牌（Refresh Token）并执行回调
     * <p>若授权信息中包含刷新令牌，则将其与过期时间传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为刷新令牌及其过期时间
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper refreshToken(Consumer<AuthorizationCacheInfo> consumer) {
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = oAuth2Authorization.getToken(OAuth2RefreshToken.class);
        if (Objects.nonNull(refreshToken)) {
            OAuth2RefreshToken token = refreshToken.getToken();
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.REFRESH_TOKEN, token.getTokenValue(), token.getExpiresAt());
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 获取授权码（Authorization Code）并执行回调
     * <p>若授权信息中包含授权码，则将其与过期时间传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为授权码令牌及其过期时间
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper authorizationCode(Consumer<AuthorizationCacheInfo> consumer) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = oAuth2Authorization.getToken(OAuth2AuthorizationCode.class);
        if (Objects.nonNull(authorizationCode)) {
            OAuth2AuthorizationCode token = authorizationCode.getToken();
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.CODE, token.getTokenValue(), token.getExpiresAt());
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 获取 State 并执行回调
     * <p>若授权信息中包含 State，则将其传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为 State
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper state(Consumer<AuthorizationCacheInfo> consumer) {
        String state = oAuth2Authorization.getAttribute(AuthorizationConstant.STATE);
        if (Objects.nonNull(state)) {
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.CODE, state, null);
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 获取 OIDC ID 令牌（Oidc Id Token）并执行回调
     * <p>若授权信息中包含 OIDC ID 令牌，则将其与过期时间传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为 OIDC ID 令牌及其过期时间
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper oidcIdToken(Consumer<AuthorizationCacheInfo> consumer) {
        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = oAuth2Authorization.getToken(OidcIdToken.class);
        if (Objects.nonNull(oidcIdToken)) {
            OidcIdToken token = oidcIdToken.getToken();
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.ID_TOKEN, token.getTokenValue(), token.getExpiresAt());
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 获取用户码（User Code）并执行回调
     * <p>若授权信息中包含用户码（设备授权流程），则将其与过期时间传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为用户码及其过期时间
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper userCode(Consumer<AuthorizationCacheInfo> consumer) {
        OAuth2Authorization.Token<OAuth2UserCode> userCode = oAuth2Authorization.getToken(OAuth2UserCode.class);
        if (Objects.nonNull(userCode)) {
            OAuth2UserCode token = userCode.getToken();
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.USER_CODE, token.getTokenValue(), token.getExpiresAt());
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 获取设备码（Device Code）并执行回调
     * <p>若授权信息中包含设备码（设备授权流程），则将其与过期时间传递给 consumer 处理</p>
     *
     * @param consumer 回调函数，参数为设备码及其过期时间
     * @return 当前包装器实例，支持链式调用
     */
    public OAuth2AuthorizationWrapper deviceCode(Consumer<AuthorizationCacheInfo> consumer) {
        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = oAuth2Authorization.getToken(OAuth2DeviceCode.class);
        if (Objects.nonNull(deviceCode)) {
            OAuth2DeviceCode token = deviceCode.getToken();
            AuthorizationCacheInfo authorizationCacheInfo = new AuthorizationCacheInfo(AuthorizationConstant.DEVICE_CODE, token.getTokenValue(), token.getExpiresAt());
            this.maxTimeout = authorizationCacheInfo.getMaxTimeout(this.maxTimeout);
            consumer.accept(authorizationCacheInfo);
        }
        return this;
    }

    /**
     * 直接访问原始 OAuth2Authorization 对象
     * <p>用于处理上述方法未覆盖的其他授权信息</p>
     *
     * @param consumer 回调函数，参数为原始 OAuth2Authorization 对象
     */
    public void maxTimeout(BiConsumer<Long,TimeUnit> consumer) {
        consumer.accept(this.maxTimeout, TimeUnit.SECONDS);
    }

    @Getter
    public static class AuthorizationCacheInfo {

        private final String key;

        private final long timeout;

        private final TimeUnit timeUnit = TimeUnit.SECONDS;

        public AuthorizationCacheInfo(String tokenType, String tokenValue, Instant expiresAt) {
            this.key = Keys.createKey(AuthorizationConstant.AUTHORIZATION_TOKEN_KEY_PREFIX, tokenType, MD5Utils.generateSignature(tokenValue));
            this.timeout = diffSecondsFromNow(expiresAt);
        }

        /**
         * 计算目标Instant到当前时间相差的总秒数
         *
         * @param endInstant 对比时间点
         * @return 正数：目标时间已经过去多少秒；负数：距离目标还有多少秒
         */
        private long diffSecondsFromNow(Instant endInstant) {
            if (endInstant == null) {
                return DEFAULT_TIMEOUT;
            }
            Duration between = Duration.between(Instant.now(), endInstant);
            return between.toSeconds();
        }

        /**
         * 设置最大超时时间
         *
         * @param timeout 超时时间
         */
        public long getMaxTimeout(long timeout) {
            return Long.max(this.timeout, timeout);
        }

    }

}
