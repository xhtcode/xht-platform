package com.xht.framework.oauth2.redis.repository;

import com.xht.framework.oauth2.redis.entity.Oauth2AuthorizationConsentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * oauth2授权信息 仓储
 *
 * @author xht
 **/
@Repository
public interface Oauth2AuthorizationConsentRepository extends CrudRepository<Oauth2AuthorizationConsentEntity, String> {

    /**
     * 根据注册客户端ID和主体名称查找用户授权信息
     *
     * @param registeredClientId 注册客户端ID
     * @param principalName      主体名称（用户标识）
     * @return OAuth2用户授权信息对象，如果未找到则返回null
     */
    Oauth2AuthorizationConsentEntity findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * 根据注册客户端ID和主体名称删除用户授权信息
     *
     * @param registeredClientId 注册客户端ID
     * @param principalName      主体名称（用户标识）
     */
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
