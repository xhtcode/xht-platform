package com.xht.boot.oauth2.entity;

import com.xht.boot.oauth2.entity.token.BasicToken;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

/**
 * 使用Repository将授权申请的认证信息缓存至redis的实体
 *
 * @author xht
 */
@Data
@RedisHash(value = "oauth2:authorization")
public class RedisOAuth2Authorization implements Serializable {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 授权申请时使用的客户端id
     */
    private String registeredClientId;

    /**
     * 授权用户姓名
     */
    private String principalName;

    /**
     * 授权申请时使用 grant_type
     */
    private String authorizationGrantType;

    /**
     * 授权申请的scope
     */
    private Set<String> authorizedScopes;

    /**
     * 授权的认证信息(当前用户)、请求信息(授权申请请求)
     */
    private String attributes;

    /**
     * 授权用户信息
     */
    private Principal principal;

    /**
     * access_token
     */
    private BasicToken accessToken;

    /**
     * refresh_token
     */
    private BasicToken refreshToken;

    /**
     * id_token
     */
    private BasicToken oidcToken;

    /**
     * device_code
     */
    private BasicToken deviceCode;

    /**
     * user_code
     */
    private BasicToken userCode;
    /**
     * 授权码
     */
    private BasicToken authorizationCode;
    /**
     * 授权申请时的scope
     */
    private Set<String> requestedScopes;


    /**
     * 授权申请时的state
     */
    @Indexed
    private String state;

}
