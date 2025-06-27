package com.xht.framework.oauth2.configurers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.oauth2.introspection.ResourceOpaqueTokenIntrospector;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author xht
 **/
@RequiredArgsConstructor
public class ResourceServerAutoConfiguration {

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

    @Bean
    public ResourceOpaqueTokenIntrospector resourceServerConfigurer(OAuth2AuthorizationService authorizationService, UserDetailsService userDetailsService) {
        return new ResourceOpaqueTokenIntrospector(authorizationService, userDetailsService);
    }
}
