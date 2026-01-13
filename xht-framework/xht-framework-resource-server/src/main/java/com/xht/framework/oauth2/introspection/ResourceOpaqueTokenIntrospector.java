package com.xht.framework.oauth2.introspection;

import com.xht.framework.oauth2.token.TokenInfoLightningCache;
import com.xht.framework.security.constant.TokenCustomizerIdConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import java.time.Instant;
import java.util.Objects;

/**
 * 资源服务器Opaque Token Introspector
 *
 * @author xht
 **/
@Slf4j
public class ResourceOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final SpringOpaqueTokenIntrospector opaqueTokenIntrospector;

    private final TokenInfoLightningCache tokenInfoLightningCache;

    private final static String TOKEN_KEY_PREFIX = "opaque:token:";


    public ResourceOpaqueTokenIntrospector(TokenInfoLightningCache tokenInfoLightningCache, OAuth2ResourceServerProperties.Opaquetoken opaquetoken) {
        this.tokenInfoLightningCache = tokenInfoLightningCache;
        this.opaqueTokenIntrospector = SpringOpaqueTokenIntrospector.withIntrospectionUri(opaquetoken.getIntrospectionUri()).clientId(opaquetoken.getClientId()).clientSecret(opaquetoken.getClientSecret()).build();
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return OAuth2AuthenticatedPrincipal
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        log.debug(">>>>>>资源服务器Opaque Token Introspector 验证令牌 {} <<<<<<", token);
        String key = TOKEN_KEY_PREFIX + token;
        BasicUserDetails tokenInfo = tokenInfoLightningCache.getTokenInfo(key);
        if (Objects.nonNull(tokenInfo)) {
         //   return tokenInfo;
        }
        OAuth2AuthenticatedPrincipal introspect = opaqueTokenIntrospector.introspect(token);
        if (introspect instanceof OAuth2IntrospectionAuthenticatedPrincipal principal) {
            Instant expiresAt = principal.getExpiresAt();
            BasicUserDetails convert = convert(principal);
            tokenInfoLightningCache.setTokenInfo(key, expiresAt, convert);
            return convert;
        }
        return introspect;
    }

    /**
     * 将OAuth2认证后的用户主体信息转换为BasicUserDetails对象
     *
     * @param principal OAuth2认证后的用户主体信息，包含用户的各种属性和权限信息
     * @return 转换后的BasicUserDetails用户详情对象，包含用户的基本信息、权限、角色等
     */
    private BasicUserDetails convert(OAuth2IntrospectionAuthenticatedPrincipal principal) {
        return principal.getClaim(TokenCustomizerIdConstant.USER_INFO);
    }

}
