package com.xht.auth.consent.service;

import com.xht.auth.consent.domain.response.SysOauth2AuthorizationConsentResponse;

import java.util.List;

/**
 * 授权确认信息服务
 *
 * @author xht
 **/
public interface ISysOauth2AuthorizationConsentService {

    /**
     * 根据用户名查询授权确认信息
     * @param userName 用户名
     * @return 授权确认信息
     */
    List<SysOauth2AuthorizationConsentResponse> findByUserName(String userName);

    /**
     * 根据注册客户端id删除授权确认信息
     * @param registeredClientId 注册客户端id
     * @param principalName 主体名称
     */
    void removeByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

}
