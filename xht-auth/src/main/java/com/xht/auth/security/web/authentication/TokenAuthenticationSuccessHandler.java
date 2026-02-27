package com.xht.auth.security.web.authentication;

import cn.hutool.core.map.MapUtil;
import com.xht.auth.authentication.dao.SysLoginLogDao;
import com.xht.auth.authentication.entity.SysLoginLogEntity;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.domain.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：认证成功处理
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private  SysLoginLogDao sysLoginLogDao;

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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("TokenAuthenticationSuccessHandler");
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        TokenResponse tokenResponse = convertToTokenResponse(accessToken, refreshToken, additionalParameters);
        SysLoginLogEntity entity = new SysLoginLogEntity();
        entity.setTraceId(TraceIdUtils.getTraceId());
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        Map<String, String> headerMap = ServletUtil.getHeaderMap(request);
        entity.setUserName(MapUtil.getStr(paramMap, SecurityConstant.REQUEST_USERNAME));
        entity.setLoginType(MapUtil.getStr(paramMap, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE));
        entity.setLoginTime(LocalDateTime.now());
        entity.setLoginIp(IpUtils.getIpAddr(request));
        // entity.setUserAgent();
        // entity.setLoginStatus(true);
        // entity.setTokenValue();
        // entity.setFailReason();
        // entity.setLoginDesc();
        ServletUtil.writeJson(response, R.ok().build(tokenResponse));
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
}
