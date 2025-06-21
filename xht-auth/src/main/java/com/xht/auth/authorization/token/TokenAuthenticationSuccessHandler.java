package com.xht.auth.authorization.token;

import com.xht.auth.domain.response.TokenResponse;
import com.xht.framework.core.domain.R;
import com.xht.framework.web.utils.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 * @author xht
 **/
@Slf4j
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("TokenAuthenticationSuccessHandler");
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        TokenResponse tokenResponse = convertToTokenResponse(accessToken, refreshToken, additionalParameters);
        HttpServletUtils.writeString(response, R.ok(tokenResponse));
    }

    private TokenResponse convertToTokenResponse(OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken, Map<String, Object> additionalParameters) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setTokenType(accessToken.getTokenType().getValue());
        tokenResponse.setAccessToken(accessToken.getTokenValue());
        tokenResponse.setScopes(accessToken.getScopes());
        if (Objects.nonNull(refreshToken)) {
            tokenResponse.setRefreshToken(refreshToken.getTokenValue());
        }
        tokenResponse.setExpiresIn(getExpiresIn(accessToken));
        tokenResponse.setAdditionalParameters(additionalParameters);
        return tokenResponse;
    }

    /**
     * 获取token的过期时间
     *
     * @param accessToken token
     * @return token的过期时间
     */
    private static long getExpiresIn(OAuth2AccessToken accessToken) {
        if (accessToken.getExpiresAt() != null) {
            return ChronoUnit.SECONDS.between(Instant.now(), accessToken.getExpiresAt());
        }
        return -1;
    }
}
