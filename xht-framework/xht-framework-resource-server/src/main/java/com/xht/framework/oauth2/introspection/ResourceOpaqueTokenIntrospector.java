package com.xht.framework.oauth2.introspection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

/**
 * 资源服务器Opaque Token Introspector
 *
 * @author xht
 **/
@Slf4j
public class ResourceOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final SpringOpaqueTokenIntrospector opaqueTokenIntrospector;

    public ResourceOpaqueTokenIntrospector(OAuth2ResourceServerProperties.Opaquetoken opaquetoken) {
        this.opaqueTokenIntrospector = SpringOpaqueTokenIntrospector
                .withIntrospectionUri(opaquetoken.getIntrospectionUri())
                .clientId(opaquetoken.getClientId())
                .clientSecret(opaquetoken.getClientSecret())
                .build();
    }

    /**
     * @param token 令牌
     * @return OAuth2AuthenticatedPrincipal
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        log.info(">>>>>>资源服务器Opaque Token Introspector 验证令牌 {} <<<<<<", token);
        return opaqueTokenIntrospector.introspect(token);
    }

}
