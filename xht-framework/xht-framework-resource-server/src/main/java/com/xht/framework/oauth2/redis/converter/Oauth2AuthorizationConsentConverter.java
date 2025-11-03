package com.xht.framework.oauth2.redis.converter;

import com.xht.framework.oauth2.redis.entity.Oauth2AuthorizationConsentEntity;
import com.xht.framework.core.converter.IConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

import java.util.Set;

/**
 * Oauth2AuthorizationConsent转换器
 *
 * @author xht
 **/
public final class Oauth2AuthorizationConsentConverter implements IConverter<OAuth2AuthorizationConsent, Oauth2AuthorizationConsentEntity> {

    /**
     * 将源对象转换为目标对象
     *
     * @param consent 源对象，非null
     * @return 转换后的目标对象，非null
     */
    @Override
    public Oauth2AuthorizationConsentEntity convert(OAuth2AuthorizationConsent consent) {
        String id = consent.getRegisteredClientId()
                .concat("-")
                .concat(consent.getPrincipalName());
        Oauth2AuthorizationConsentEntity entity = new Oauth2AuthorizationConsentEntity();
        entity.setId(id);
        entity.setRegisteredClientId(consent.getRegisteredClientId());
        entity.setPrincipalName(consent.getPrincipalName());
        Set<GrantedAuthority> authorities = consent.getAuthorities();
        System.out.println("类型查看 authorities");
        for (GrantedAuthority authority : authorities) {
            System.out.println(authority.getClass());
        }
        entity.setAuthorities(consent.getAuthorities());
        return entity;
    }

    /**
     * 将目标对象反转转换为源对象
     *
     * @param entity 目标对象，非null
     * @return 反转转换后的源对象，非null
     */
    @Override
    public OAuth2AuthorizationConsent reverse(Oauth2AuthorizationConsentEntity entity) {
        return OAuth2AuthorizationConsent.withId(entity.getId(), entity.getPrincipalName())
                .authorities((authorities) -> authorities.addAll(entity.getAuthorities()))
                .build();
    }
}
