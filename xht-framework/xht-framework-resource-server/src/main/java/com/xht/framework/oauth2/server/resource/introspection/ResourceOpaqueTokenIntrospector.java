package com.xht.framework.oauth2.server.resource.introspection;

import com.xht.framework.oauth2.redis.converter.Oauth2AuthorizationConverter;
import com.xht.framework.oauth2.redis.entity.Oauth2AuthorizationEntity;
import com.xht.framework.oauth2.redis.repository.Oauth2AuthorizationRepository;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.Objects;

/**
 * 资源服务器Opaque Token Introspector
 *
 * @author xht
 **/
@Slf4j
public class ResourceOpaqueTokenIntrospector implements OpaqueTokenIntrospector {


    private final Oauth2AuthorizationRepository oauth2AuthorizationRepository;

    public ResourceOpaqueTokenIntrospector(Oauth2AuthorizationRepository oauth2AuthorizationRepository) {
        this.oauth2AuthorizationRepository = oauth2AuthorizationRepository;
    }

    /**
     * @param token 令牌
     * @return OAuth2AuthenticatedPrincipal
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        // 低命中率且数据庞大放redis稳妥，分布式集群需要通过redis实现数据共享
        Oauth2AuthorizationEntity entity = oauth2AuthorizationRepository.findByAccessTokenValue(token).orElseThrow(() -> new OAuth2AuthenticationException("登录状态已过期"));
        Oauth2AuthorizationConverter oauth2AuthorizationConverter = new Oauth2AuthorizationConverter();
        OAuth2Authorization authorization = oauth2AuthorizationConverter.reverse(entity);
        // 客户端模式默认返回
        if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(authorization.getAuthorizationGrantType())) {
            return new DefaultOAuth2AuthenticatedPrincipal(authorization.getPrincipalName(),
                    Objects.requireNonNull(authorization.getAccessToken().getClaims()),
                    AuthorityUtils.NO_AUTHORITIES);
        }
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (ObjectUtils.isEmpty(accessToken) || ObjectUtils.isEmpty(refreshToken)) {
            throw new OAuth2AuthenticationException("登录状态已过期");
        }
        if (accessToken.isActive()) {
            Object obj = authorization.getAttribute(Principal.class.getName());
            if (obj instanceof UsernamePasswordAuthenticationToken passwordAuthenticationToken) {
                return (BasicUserDetails) passwordAuthenticationToken.getDetails();
            }
        } else {
            oauth2AuthorizationRepository.deleteById(entity.getId());
        }
        throw new OAuth2AuthenticationException("登录状态已过期");
    }

}
