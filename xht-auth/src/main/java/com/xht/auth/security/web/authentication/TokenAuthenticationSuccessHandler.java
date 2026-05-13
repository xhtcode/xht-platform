package com.xht.auth.security.web.authentication;

import com.xht.auth.constant.CustomAuthorizationGrantType;
import com.xht.framework.core.constant.StringConstant;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.security.response.TokenResponse;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：认证成功处理
 *
 * @author xht
 **/
@Slf4j
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.debug("token 端点认证成功处理器");
        if (!(authentication instanceof OAuth2AccessTokenAuthenticationToken accessTokenAuthentication)) {
            log.error("{} must be of type {} but was {}", Authentication.class.getSimpleName(), OAuth2AccessTokenAuthenticationToken.class.getName(), authentication.getClass().getName());
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "Unable to process the access token response.", null);
            throw new OAuth2AuthenticationException(error);
        }
        String grant_type = request.getParameter(TokenCustomizerIdConstant.GRANT_TYPE);
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        TokenResponse tokenResponse = convertToTokenResponse(accessToken, refreshToken, additionalParameters);
        if (StringUtils.equals(grant_type, CustomAuthorizationGrantType.PASSWORD.getValue()) || StringUtils.equals(grant_type, CustomAuthorizationGrantType.PHONE.getValue())) {
            ServletUtil.writeJson(response, R.ok().build(tokenResponse));
        } else {
            ServletUtil.writeJson(response, tokenResponse);
        }
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
        tokenResponse.setTokenType(accessToken.getTokenType().getValue());
        tokenResponse.setScopes(StringUtils.collectionToDelimitedString(accessToken.getScopes(), StringConstant.SPACE));
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
        if (Objects.nonNull(accessToken.getIssuedAt()) && Objects.nonNull(accessToken.getExpiresAt())) {
            return ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
        }
        return -1;
    }

}
