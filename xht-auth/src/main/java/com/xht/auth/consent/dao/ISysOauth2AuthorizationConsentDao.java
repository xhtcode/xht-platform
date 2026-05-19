package com.xht.auth.consent.dao;

import com.xht.auth.consent.entity.SysOauth2AuthorizationConsentEntity;
import com.xht.framework.mybatis.repository.MapperRepository;

import java.util.List;

/**
 * 授权确认信息数据访问接口
 *
 * @author xht
 **/
public interface ISysOauth2AuthorizationConsentDao extends MapperRepository<SysOauth2AuthorizationConsentEntity> {

    /**
     * 根据注册客户端id和主体名称删除授权确认信息
     * @param registeredClientId 注册客户端id
     * @param principalName 主体名称
     */
    void removeByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * 根据注册客户端id和主体名称查询授权确认信息
     * @param registeredClientId 注册客户端id
     * @param principalName 主体名称
     * @return 授权确认信息
     */
    List<SysOauth2AuthorizationConsentEntity> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * 根据主体名称查询授权确认信息
     * @param principalName 主体名称
     * @return 授权确认信息
     */
    List<SysOauth2AuthorizationConsentEntity> findByPrincipalName(String principalName);
}
