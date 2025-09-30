package com.xht.boot.oauth2.repository;


import com.xht.boot.oauth2.entity.OAuth2UserConsent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OAuth2用户授权信息仓库接口
 * 用于管理OAuth2用户的授权同意记录，提供用户对客户端授权信息的存储和查询功能
 * @author xht
 */
@Repository
public interface OAuth2UserConsentRepository extends CrudRepository<OAuth2UserConsent, String> {

    /**
     * 根据注册客户端ID和主体名称查找用户授权信息
     *
     * @param registeredClientId 注册客户端ID
     * @param principalName      主体名称（用户标识）
     * @return OAuth2用户授权信息对象，如果未找到则返回null
     */
    OAuth2UserConsent findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * 根据注册客户端ID和主体名称删除用户授权信息
     *
     * @param registeredClientId 注册客户端ID
     * @param principalName 主体名称（用户标识）
     */
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

}
