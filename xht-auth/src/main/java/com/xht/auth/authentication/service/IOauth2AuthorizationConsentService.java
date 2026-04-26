package com.xht.auth.authentication.service;

import com.xht.auth.authentication.domain.vo.Oauth2ConsentVo;

/**
 * 授权确认信息
 *
 * @author xht
 **/
public interface IOauth2AuthorizationConsentService {

    /**
     * 获取授权确认信息
     *
     * @param scope    授权范围
     * @param clientId 客户端id
     * @param state    状态参数
     * @param userCode  设备码授权流程中的用户码
     * @return 获取授权确认信息
     */
    Oauth2ConsentVo getConsentInfo(String scope, String clientId, String state, String userCode);


}
