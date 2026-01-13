package com.xht.auth.redis.entity;

import com.xht.framework.oauth2.constant.Oauth2Constant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * oauth2认证确认信息
 *
 * @author xht
 */
@Data
@RedisHash(value = Oauth2Constant.AUTHORIZATION_CONSENT_KEY_PREFIX, timeToLive = 10L)
public class Oauth2AuthorizationConsentEntity {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 授权确认的客户端
     */
    @Indexed
    private String registeredClientId;

    /**
     * 授权确认的用户
     */
    @Indexed
    private String principalName;

    /**
     * 授权确认的scope
     */
    private Set<GrantedAuthority> authorities;

}
