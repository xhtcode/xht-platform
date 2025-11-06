package com.xht.framework.oauth2.configurers;

import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.oauth2.introspection.ResourceOpaqueTokenIntrospector;
import com.xht.framework.oauth2.token.RedisTokenInfoLightningCache;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * 资源服务器自动配置
 *
 * @author xht
 **/
@Slf4j
public class SecurityAutoConfiguration {
    public SecurityAutoConfiguration() {
        log.info("开始加载资源服务器异常处理");
    }

    /**
     * 资源服务器异常处理
     *
     * @return ResourceAuthenticationEntryPoint
     */
    @Bean
    public ResourceAuthenticationEntryPoint permitAllUrlProperties() {
        return new ResourceAuthenticationEntryPoint();
    }

    /**
     * 资源服务器token处理
     *
     * @return ResourceBearerTokenResolver
     */
    @Bean
    public ResourceBearerTokenResolver bearerTokenHandler(PermitAllUrlProperties permitAllUrlProperties) {
        return new ResourceBearerTokenResolver(permitAllUrlProperties);
    }


    @Bean
    @ConditionalOnMissingBean(TokenInfoLightningCache.class)
    @ConditionalOnClass(RedisTemplate.class)
    public TokenInfoLightningCache tokenInfoLightningCache(RedisTemplate<String, Object> redisTemplate) {
        return new RedisTokenInfoLightningCache(redisTemplate);
    }


    /**
     * 资源服务器token验证
     *
     * @return OpaqueTokenIntrospector
     */
    @Bean
    public ResourceOpaqueTokenIntrospector resourceServerConfigurer(TokenInfoLightningCache tokenInfoLightningCache, OAuth2ResourceServerProperties resourceServerProperties) throws Exception {
        OAuth2ResourceServerProperties.Opaquetoken opaquetoken = resourceServerProperties.getOpaquetoken();
        if (Objects.isNull(opaquetoken)) {
            log.error("未配置Opaque Token相关属性，无法注入 ResourceOpaqueTokenIntrospector.");
            throw new Exception("未配置Opaque Token相关属性，无法注入 ResourceOpaqueTokenIntrospector.");
        }
        return new ResourceOpaqueTokenIntrospector(tokenInfoLightningCache, opaquetoken);
    }
}