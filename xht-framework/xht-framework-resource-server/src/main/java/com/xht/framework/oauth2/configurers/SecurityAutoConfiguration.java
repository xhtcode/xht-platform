package com.xht.framework.oauth2.configurers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.oauth2.redis.repository.Oauth2AuthorizationRepository;
import com.xht.framework.oauth2.server.resource.introspection.ResourceOpaqueTokenIntrospector;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * 资源服务器自动配置
 *
 * @author xht
 **/
@Slf4j
@EnableRedisRepositories(basePackages = "com.xht.framework.oauth2.redis.repository")
public class SecurityAutoConfiguration {

    /**
     * 资源服务器异常处理
     *
     * @param objectMapper jackson 输出对象
     * @return ResourceAuthenticationEntryPoint
     */
    @Bean
    public ResourceAuthenticationEntryPoint permitAllUrlProperties(ObjectMapper objectMapper) {
        return new ResourceAuthenticationEntryPoint(objectMapper);
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

    /**
     * 资源服务器token验证
     *
     * @return ResourceOpaqueTokenIntrospector
     */
    @Bean
    @SuppressWarnings("all")
    public ResourceOpaqueTokenIntrospector resourceServerConfigurer(Oauth2AuthorizationRepository oauth2AuthorizationRepository) throws Exception {
        return new ResourceOpaqueTokenIntrospector(oauth2AuthorizationRepository);
    }
}
