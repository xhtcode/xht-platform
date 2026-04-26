package com.xht.auth.authentication.service.impl;

import com.xht.auth.authentication.dao.ISysOauth2ClientScopeDao;
import com.xht.auth.authentication.domain.response.SysOauth2ClientScopeResponse;
import com.xht.auth.authentication.domain.vo.Oauth2ConsentVo;
import com.xht.auth.authentication.service.IOauth2AuthorizationConsentService;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
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

    private final OAuth2AuthorizationConsentService authorizationConsentService;

    private final ISysOauth2ClientScopeDao sysOauth2ClientScopeDao;

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
    public Oauth2ConsentVo getConsentInfo(String scope, String clientId, String state, String userCode) {
        log.info("授权范围:{},客户端id:{},状态参数:{},userCode:{}", scope, clientId, state, userCode);
        // 携带当前请求参数与nonceId重定向至前端页面
        BasicUserDetails user = SecurityUtils.getUser();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            throw new OAuth2AuthenticationException(String.format("客户端`%s`不存在", clientId));
        }
        // 默认选择的请求范围
        Set<String> defaultScopes = new HashSet<>();
        // 所有的请求范围
        List<SysOauth2ClientScopeResponse> scopeResponses = sysOauth2ClientScopeDao.findByClientId(clientId);
        Set<String> allScopes = registeredClient.getScopes();
        // 请求的请求范围
        String[] requestScopes = StringUtils.delimitedListToStringArray(scope, " ");
        for (String item : requestScopes) {
            if (!allScopes.contains(item)) {
                throw new OAuth2AuthenticationException(String.format("非法请求,请求范围`%s`不存在", item));
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

}
