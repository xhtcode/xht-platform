package com.xht.auth.redis.repository;

import com.xht.auth.redis.entity.Oauth2AuthorizationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * oauth2认证信息 仓储
 *
 * @author xht
 **/
@Repository
public interface Oauth2AuthorizationRepository extends CrudRepository<Oauth2AuthorizationEntity, String> {

    /**
     * 根据{@code authorizationCodeValue}获取认证信息
     *
     * @param authorizationCodeValue 授权码值
     * @return 认证信息
     */
    Optional<Oauth2AuthorizationEntity> findByAuthorizationCodeValue(String authorizationCodeValue);

    /**
     * 根据{@code accessTokenValue}获取认证信息
     *
     * @param accessTokenValue 认证后签发的access token
     * @return 认证信息
     */
    Optional<Oauth2AuthorizationEntity> findByAccessTokenValue(String accessTokenValue);

    /**
     * 根据{@code refreshTokenValue}获取认证信息
     *
     * @param refreshTokenValue 认证后签发的 refresh token
     * @return 认证信息
     */
    Optional<Oauth2AuthorizationEntity> findByRefreshTokenValue(String refreshTokenValue);

    /**
     * 根据{@code oidcIdTokenValue}获取认证信息
     *
     * @param oidcIdTokenValue id token
     * @return 认证后签发的 oidc id token
     */
    Optional<Oauth2AuthorizationEntity> findByOidcIdTokenValue(String oidcIdTokenValue);

    /**
     * 根据{@code userCodeValue}获取认证信息
     *
     * @param userCodeValue 备码模式(Device Flow)中的 user code
     * @return 认证信息
     */
    Optional<Oauth2AuthorizationEntity> findByUserCodeValue(String userCodeValue);

    /**
     * 根据{@code deviceCodeValue}获取认证信息
     *
     * @param deviceCodeValue 设备码模式(Device Flow)中的 device code
     * @return 认证信息
     */
    Optional<Oauth2AuthorizationEntity> findByDeviceCodeValue(String deviceCodeValue);

    /**
     * 根据{@code state}获取认证信息
     *
     * @param state 状态值
     * @return 认证信息
     */
    Optional<Oauth2AuthorizationEntity> findByState(String state);

}
