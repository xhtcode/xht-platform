package com.xht.framework.oauth2.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * oauth2认证信息
 *
 * @author xht
 */
@Data
@RedisHash("oauth2:authorization")
public class Oauth2AuthorizationEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 数据id
     */
    @Id
    private String id;

    /**
     * 认证时使用的客户端id
     */
    private String registeredClientId;

    /**
     * 认证用户名
     */
    private String principalName;

    /**
     * 认证时使用的模式
     * 如：authorization_code（授权码模式）、password（密码模式）、client_credentials（客户端模式）等
     */
    private String authorizationGrantType;

    /**
     * 用户授权申请的scope
     * 最终存储的还是授权确认的scope
     */
    private String authorizedScopes;

    /**
     * 授权过程中的附加属性
     */
    private String attributes;

    /**
     * 状态值
     * 用于防止CSRF攻击的随机状态字符串，在授权请求中生成并验证
     */
    @Indexed
    private String state;

    /**
     * 授权码值
     */
    @Indexed
    private String authorizationCodeValue;

    /**
     * 授权码签发时间
     */
    private LocalDateTime authorizationCodeIssuedAt;

    /**
     * 授权码过期时间
     */
    private LocalDateTime authorizationCodeExpiresAt;

    /**
     * 授权码元数据
     */
    private String authorizationCodeMetadata;

    /**
     * 认证后签发的access token
     */
    @Indexed
    private String accessTokenValue;

    /**
     * access token 签发时间
     */
    private LocalDateTime accessTokenIssuedAt;

    /**
     * access token 过期时间
     */
    private LocalDateTime accessTokenExpiresAt;

    /**
     * access token 元数据
     */
    private String accessTokenMetadata;

    /**
     * access token类型
     * 如：bearer（默认）、mac等
     */
    private String accessTokenType;

    /**
     * access token中包含的scope
     * 授权确认过的scope
     */
    private String accessTokenScopes;

    /**
     * 认证后签发的 oidc id token
     */
    @Indexed
    private String oidcIdTokenValue;

    /**
     * oidc id token 签发时间
     */
    private LocalDateTime oidcIdTokenIssuedAt;

    /**
     * oidc id token 过期时间
     */
    private LocalDateTime oidcIdTokenExpiresAt;

    /**
     * oidc id token 元数据
     */
    private String oidcIdTokenMetadata;

    /**
     * 认证后签发的 refresh token
     */
    @Indexed
    private String refreshTokenValue;

    /**
     * refresh token 签发时间
     */
    private LocalDateTime refreshTokenIssuedAt;

    /**
     * refresh token 过期时间
     */
    private LocalDateTime refreshTokenExpiresAt;

    /**
     * refresh token 元数据
     */
    private String refreshTokenMetadata;

    /**
     * 设备码模式(Device Flow)中的 user code
     */
    @Indexed
    private String userCodeValue;

    /**
     * user code 签发时间
     */
    private LocalDateTime userCodeIssuedAt;

    /**
     * user code 过期时间
     */
    private LocalDateTime userCodeExpiresAt;

    /**
     * user code 元数据
     */
    private String userCodeMetadata;

    /**
     * 设备码模式(Device Flow)中的 device code
     */
    @Indexed
    private String deviceCodeValue;

    /**
     * device code 签发时间
     */
    private LocalDateTime deviceCodeIssuedAt;

    /**
     * device code 过期时间
     */
    private LocalDateTime deviceCodeExpiresAt;

    /**
     * device code 元数据
     */
    private String deviceCodeMetadata;

    /**
     * 当前对象在Redis中的过期时间
     */
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long timeout;

}