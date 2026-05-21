package com.xht.auth.security.oauth2.server.authorization.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * oauth2 授权成功 成功处理器
 *
 * @author xht
 **/
@Slf4j
public class AuthorizationEndpointSuccessHandler implements AuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("授权成功 {} {} {}",request.getMethod(),authentication.isAuthenticated(),authentication.getClass().getName());
        OAuth2AuthorizationCodeRequestAuthenticationToken authenticationToken = (OAuth2AuthorizationCodeRequestAuthenticationToken) authentication;
        String redirectUri = authenticationToken.getRedirectUri();
        if (!StringUtils.hasText(redirectUri)) {
            log.error("Invalid or missing redirect_uri");
            ServletUtil.writeJson(response, R.error().msg("Invalid or missing redirect_uri").build());
            return;
        }
        String tokenValue = Optional.ofNullable(authenticationToken.getAuthorizationCode()).map(AbstractOAuth2Token::getTokenValue).orElse(null);
        String state = authenticationToken.getState();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUri);
        uriBuilder.queryParam(OAuth2ParameterNames.CODE, tokenValue);
        if (StringUtils.hasText(state)) {
            uriBuilder.queryParam(OAuth2ParameterNames.STATE, UriUtils.encode(state, StandardCharsets.UTF_8));
        }
        AuthorizationEndpointSuccessResponse endpointResponse = new AuthorizationEndpointSuccessResponse();
        endpointResponse.setUsername(authenticationToken.getName());
        endpointResponse.setCode(tokenValue);
        endpointResponse.setState(state);
        endpointResponse.setRedirectUri(uriBuilder.build(true).toUriString());
        if (Objects.equals(request.getMethod(), HttpConstants.Method.GET.getValue())) {
            redirectStrategy.sendRedirect(request, response, endpointResponse.getRedirectUri());
        } else {
            ServletUtil.writeJson(response, R.ok().build(endpointResponse));
        }
    }

    /**
     * 授权响应
     *
     * @author xht
     **/
    @Data
    public static class AuthorizationEndpointSuccessResponse extends BasicResponse {

        /**
         * 用户名称
         */
        @JsonProperty(OAuth2ParameterNames.USERNAME)
        private String username;

        /**
         * 授权码
         */
        @JsonProperty(OAuth2ParameterNames.CODE)
        private String code;

        /**
         * 状态
         */
        @JsonProperty(OAuth2ParameterNames.STATE)
        private String state;

        /**
         * 重定向地址
         */
        @JsonProperty(OAuth2ParameterNames.REDIRECT_URI)
        private String redirectUri;

    }
}
