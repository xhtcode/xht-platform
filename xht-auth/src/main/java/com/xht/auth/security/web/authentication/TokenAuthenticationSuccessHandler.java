package com.xht.auth.security.web.authentication;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.security.domain.response.TokenResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：认证成功处理
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("TokenAuthenticationSuccessHandler");
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        TokenResponse tokenResponse = convertToTokenResponse(accessToken, refreshToken, additionalParameters);
        ServletUtil.write(response, R.ok(tokenResponse));
    }

    /**
     * 构建token返回值
     *
     * @param accessToken          访问令牌
     * @param refreshToken         刷新令牌
     * @param additionalParameters 额外的参数
     * @return TokenResponse
     */
    private TokenResponse convertToTokenResponse(OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken, Map<String, Object> additionalParameters) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken.getTokenValue());
        if (Objects.nonNull(refreshToken)) {
            tokenResponse.setRefreshToken(refreshToken.getTokenValue());
        }
        tokenResponse.setExpiresIn(getExpiresIn(accessToken));
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            tokenResponse.setAdditionalParameters(additionalParameters);
        }
        return tokenResponse;
    }

    /**
     * 获取token的过期时间
     *
     * @param accessToken token
     * @return token的过期时间
     */
    private static long getExpiresIn(OAuth2AccessToken accessToken) {
        if (Objects.nonNull(accessToken.getExpiresAt())) {
            return ChronoUnit.SECONDS.between(Instant.now(), accessToken.getExpiresAt());
        }
        return -1;
    }
}
