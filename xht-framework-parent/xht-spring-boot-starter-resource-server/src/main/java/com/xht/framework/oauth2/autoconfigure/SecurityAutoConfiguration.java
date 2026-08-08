package com.xht.framework.oauth2.autoconfigure;

import com.xht.framework.oauth2.handler.PermissionCheckHandler;
import com.xht.framework.oauth2.introspection.ResourceOpaqueTokenIntrospector;
import com.xht.framework.oauth2.token.RedisTokenInfoLightningCache;
import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import com.xht.framework.oauth2.token.TokenLightningCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.Objects;

/**
 * 资源服务器自动配置
 *
 * @author xht
 **/
@Slf4j
@EnableConfigurationProperties(TokenLightningCacheProperties.class)
public class SecurityAutoConfiguration {

    public SecurityAutoConfiguration() {
        log.info("【xht-spring-boot-starter-resource-server】开始加载资源服务器异常处理");
    }

    /**
     * <a href="https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-intercept-methods">参考地址<a/>
     * <p>
     * 创建注解模板表达式默认配置Bean
     *
     * @return AnnotationTemplateExpressionDefaults 注解模板表达式默认配置实例
     */
    @Bean
    static AnnotationTemplateExpressionDefaults templateExpressionDefaults() {
        return new AnnotationTemplateExpressionDefaults();
    }


    @Bean
    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnMissingBean(TokenInfoLightningCache.class)
    public TokenInfoLightningCache tokenInfoLightningCache(RedisTemplate<String, Object> redisTemplate, TokenLightningCacheProperties tokenLightningCacheProperties) {
        return new RedisTokenInfoLightningCache(redisTemplate, tokenLightningCacheProperties);
    }

    /**
     * 资源服务器token验证
     *
     * @return OpaqueTokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector resourceServerConfigurer(TokenInfoLightningCache tokenInfoLightningCache, OAuth2ResourceServerProperties resourceServerProperties) throws Exception {
        OAuth2ResourceServerProperties.Opaquetoken opaquetoken = resourceServerProperties.getOpaquetoken();
        if (Objects.isNull(opaquetoken)) {
            log.error("未配置Opaque Token相关属性，无法注入 ResourceOpaqueTokenIntrospector.");
            throw new Exception("未配置Opaque Token相关属性，无法注入 ResourceOpaqueTokenIntrospector.");
        }
        return new ResourceOpaqueTokenIntrospector(tokenInfoLightningCache, opaquetoken);
    }

    /**
     * 创建并配置OAuth2权限检查处理器Bean
     *
     * @return PermissionCheckHandler 权限检查处理器实例
     */
    @Bean("xht")
    public PermissionCheckHandler permissionCheckHandler() {
        return new PermissionCheckHandler();
    }

}