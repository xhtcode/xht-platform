package com.xht.auth.authentication.service.impl;

import com.xht.auth.authentication.dao.ISysOauth2ClientScopeDao;
import com.xht.auth.authentication.domain.response.SysOauth2ClientScopeResponse;
import com.xht.auth.authentication.domain.vo.Oauth2ConsentVo;
import com.xht.auth.authentication.service.IOauth2AuthorizationConsentService;
import com.xht.auth.constant.AuthorizationConstant;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 授权确认信息
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2AuthorizationConsentServiceImpl implements IOauth2AuthorizationConsentService {

    private final RegisteredClientRepository registeredClientRepository;

    private final ISysOauth2ClientScopeDao sysOauth2ClientScopeDao;

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";

    /**
     * 获取授权确认信息
     *
     * @param scope    授权范围
     * @param clientId 客户端id
     * @param state    状态参数
     * @param userCode  设备码授权流程中的用户码
     * @return 获取授权确认信息
     */
    @Override
    @SuppressWarnings("all")
    public Oauth2ConsentVo getConsentInfo(String scope, String clientId, String state, String userCode) {
        log.info("授权范围:{},客户端id:{},状态参数:{},userCode:{}", scope, clientId, state, userCode);
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            // 客户端信息匹配失败 抛出异常
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CLIENT_ID, String.format("客户端`%s`不存在", clientId));
        }
        OAuth2Authorization authorization = oAuth2AuthorizationService.findByToken(state, AuthorizationConstant.STATE_TOKEN_TYPE);
        if (authorization == null) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.STATE, "未查询到有效授权资料");
        }
        Authentication principal = SecurityUtils.getAuthentication()
                .orElseThrow(() -> {
                    throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.STATE, "未查询到有效授权资料");
                    return null;
                });
        if (!isPrincipalAuthenticated(principal) || !principal.getName().equals(authorization.getPrincipalName())) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.STATE, "当前请求不符合访问规范");
        }
        // 携带当前请求参数与nonceId重定向至前端页面
        BasicUserDetails user = SecurityUtils.getUser();
        if (!registeredClient.getId().equals(authorization.getRegisteredClientId())) {
            // 客户端信息匹配失败 抛出异常
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CLIENT_ID, String.format("客户端`%s`信息匹配失败", clientId));
        }
        // 请求的请求范围
        String[] requestScopes = StringUtils.delimitedListToStringArray(scope, " ");
        // 请求地址的scope范围
        OAuth2AuthorizationRequest authorizationRequest = authorization.getAttribute(OAuth2AuthorizationRequest.class.getName());
        Set<String> authorizedScopes = Optional.ofNullable(authorizationRequest).map(OAuth2AuthorizationRequest::getScopes).orElseGet(Collections::emptySet);
        // 默认选择的请求范围
        Set<String> defaultScopes = new HashSet<>();
        // 所有的请求范围
        List<SysOauth2ClientScopeResponse> scopeResponses = sysOauth2ClientScopeDao.findByClientId(clientId);
        Set<String> allScopes = Optional.ofNullable(registeredClient).map(RegisteredClient::getScopes).orElseGet(Collections::emptySet);
        for (String item : requestScopes) {
            if (!authorizedScopes.contains(item)) {
                throwError(OAuth2ErrorCodes.INVALID_SCOPE, OAuth2ParameterNames.SCOPE, String.format("授权范围`%s`信息匹配失败", item));
            }
            if (!allScopes.contains(item)) {
                throwError(OAuth2ErrorCodes.INVALID_SCOPE, OAuth2ParameterNames.SCOPE, String.format("授权范围`%s`信息匹配失败", item));
            }
            defaultScopes.add(item);
        }
        List<SysOauth2ClientScopeResponse> resultScope = new ArrayList<>();
        Map<String, SysOauth2ClientScopeResponse> allScopeMap = scopeResponses.stream().collect(Collectors.toMap(SysOauth2ClientScopeResponse::getScopeName, Function.identity(), (key1, key2) -> key2));
        for (String defaultScope : defaultScopes) {
            if (OidcScopes.OPENID.equals(defaultScope)) {
                continue;
            }
            SysOauth2ClientScopeResponse response = allScopeMap.get(defaultScope);
            if (defaultScopes.contains(response.getScopeName())) {
                response.setValue(Boolean.TRUE);
            }
            if (response.isDisabled()) {
                response.setValue(Boolean.TRUE);
            }
            resultScope.add(response);
        }
        Oauth2ConsentVo oauth2ConsentVo = new Oauth2ConsentVo();
        oauth2ConsentVo.setUsername(user.getUsername());
        oauth2ConsentVo.setClientId(clientId);
        oauth2ConsentVo.setClientName(registeredClient.getClientName());
        oauth2ConsentVo.setState(state);
        oauth2ConsentVo.setUserCode(userCode);
        oauth2ConsentVo.setScopes(resultScope);
        return oauth2ConsentVo;
    }

    private static boolean isPrincipalAuthenticated(Authentication principal) {
        return principal != null && !AnonymousAuthenticationToken.class.isAssignableFrom(principal.getClass())
                && principal.isAuthenticated();
    }


    /**
     * 抛出错误
     * @param errorCode 错误码
     * @param parameterName 参数名称
     * @param errorMessage 错误信息
     * @throws OAuth2AuthenticationException 抛出错误
     */
    private static void throwError(String errorCode, String parameterName, String errorMessage) {
        OAuth2Error error = new OAuth2Error(errorCode, String.format("OAuth 2.0 Parameter: %s", parameterName), ERROR_URI);
        throw new OAuth2AuthenticationException(error, errorMessage);
    }

}
