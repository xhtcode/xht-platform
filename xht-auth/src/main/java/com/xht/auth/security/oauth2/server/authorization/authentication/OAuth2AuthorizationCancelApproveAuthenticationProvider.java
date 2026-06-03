package com.xht.auth.security.oauth2.server.authorization.authentication;

import com.xht.framework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2DeviceAuthorizationConsentAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

/**
 * 描述：用户取消授权请求provider
 *
 * @author xht
 **/
@Slf4j
public record OAuth2AuthorizationCancelApproveAuthenticationProvider(
        OAuth2AuthorizationService authorizationService) implements AuthenticationProvider {

    private final static String CANCEL_APPROVE_YES = "true";

    private static final OAuth2TokenType STATE_TOKEN_TYPE = new OAuth2TokenType(OAuth2ParameterNames.STATE);

    public OAuth2AuthorizationCancelApproveAuthenticationProvider {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof OAuth2DeviceAuthorizationConsentAuthenticationToken) {
            return null;
        }
        OAuth2AuthorizationConsentAuthenticationToken authenticationToken = (OAuth2AuthorizationConsentAuthenticationToken) authentication;
        Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
        String cancelApprove = (String) additionalParameters.get("cancelApprove");
        if (StringUtils.equals(CANCEL_APPROVE_YES, cancelApprove)) {
            OAuth2Authorization authorization = authorizationService
                    .findByToken(authenticationToken.getState(), STATE_TOKEN_TYPE);
            String redirectUri = Optional.ofNullable(authorization)
                    .map(item -> (OAuth2AuthorizationRequest) item.getAttribute(OAuth2AuthorizationRequest.class.getName()))
                    .map(OAuth2AuthorizationRequest::getRedirectUri).orElse(null);
            return new OAuth2AuthorizationCancelApproveAuthenticationToken(
                    redirectUri,
                    authenticationToken.getPrincipal());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthorizationConsentAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
