package com.xht.boot.oauth2.entity;

import com.xht.boot.oauth2.entity.token.BasicToken;
import com.xht.boot.oauth2.entity.token.ClaimsHolder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 使用Repository将授权申请的认证信息缓存至redis的实体
 *
 * @author xht
 */
@Data
@RedisHash(value = "authorization")
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
    private String authorizedScopes;

    /**
     * 授权的认证信息(当前用户)、请求信息(授权申请请求)
     */
    private ClaimsHolder attributes;

    /**
     * 授权申请时的state
     */
    @Indexed
    private String state;

    /**
     * 授权码
     */
    private BasicToken authorizationCode;

    /**
     * access token
     */
    private BasicToken accessToken;

    /**
     * refresh token
     */
    private BasicToken refreshToken;

    /**
     * id token
     */
    private BasicToken oidcToken;

    /**
     * 用户码
     */
    private BasicToken userCode;

    /**
     * 设备码
     */
    private BasicToken deviceCode;

    /**
     * 当前对象在Redis中的过期时间
     */
    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long timeout;

}
