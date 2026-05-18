package com.xht.auth.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  rememberMe属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "xht.security.remember-me")
public class RememberMeProperties {

    /**
     * 记住我缓存键名
     */
    private String cacheKey;

    /**
     * 记住我参数名称
     */
    private String parameter;

    /**
     * 记住我Cookie名称
     */
    private String cookieName;

    /**
     * Token有效期（秒）
     */
    private Long tokenValiditySeconds;

}