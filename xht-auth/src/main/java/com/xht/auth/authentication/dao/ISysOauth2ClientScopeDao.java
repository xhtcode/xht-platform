package com.xht.auth.authentication.dao;

import com.xht.auth.authentication.domain.response.SysOauth2ClientScopeResponse;

import java.util.List;

/**
 * oauth2 客户端授权范围Dao接口
 *
 * @author xht
 **/
public interface ISysOauth2ClientScopeDao {

    /**
     * 根据客户端id查询授权范围
     * @param clientId 客户端id
     * @return 授权范围
     */
    List<SysOauth2ClientScopeResponse> findByClientId(String clientId);
}
