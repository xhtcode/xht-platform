package com.xht.boot.oauth2.repository;

import com.xht.boot.oauth2.entity.RedisOAuth2Authorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * oauth2授权管理
 *
 * @author xht
 */
@Repository
public interface RedisOAuth2AuthorizationRepository extends CrudRepository<RedisOAuth2Authorization, String> {

    /**
     * 根据授权码获取认证信息
     *
     * @param token 授权码
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByAuthorizationCodeTokenValue(String token);

    /**
     * 根据access token获取认证信息
     *
     * @param token access token
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByAccessTokenTokenValue(String token);

    /**
     * 根据刷新token获取认证信息
     *
     * @param token 刷新token
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByRefreshTokenTokenValue(String token);

    /**
     * 根据id token获取认证信息
     *
     * @param token id token
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByOidcTokenTokenValue(String token);

    /**
     * 根据用户码获取认证信息
     *
     * @param token 用户码
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByUserCodeTokenValue(String token);

    /**
     * 根据设备码获取认证信息
     *
     * @param token 设备码
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByDeviceCodeTokenValue(String token);

    /**
     * 根据state获取认证信息
     *
     * @param token 授权申请时的state
     * @return 认证信息
     */
    Optional<RedisOAuth2Authorization> findByState(String token);
}
