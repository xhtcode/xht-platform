package com.xht.auth.security.oatuh2.server.authorization;

import com.xht.auth.redis.converter.Oauth2AuthorizationConsentConverter;
import com.xht.auth.redis.entity.Oauth2AuthorizationConsentEntity;
import com.xht.auth.redis.repository.Oauth2AuthorizationConsentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;

/**
 * 授权确认信息
 *
 * @author xht
 **/
@Slf4j
@Service
public class RedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final Oauth2AuthorizationConsentRepository oauth2AuthorizationConsentRepository;

    private final Oauth2AuthorizationConsentConverter consentConverter;

    @Autowired
    public RedisOAuth2AuthorizationConsentService(Oauth2AuthorizationConsentRepository oauth2AuthorizationConsentRepository) {
        Assert.notNull(oauth2AuthorizationConsentRepository, "授权确认信息仓储 不能是空的");
        this.oauth2AuthorizationConsentRepository = oauth2AuthorizationConsentRepository;
        this.consentConverter = new Oauth2AuthorizationConsentConverter();
    }

    /**
     * 保存 {@link OAuth2AuthorizationConsent}。
     *
     * @param authorizationConsent 要保存的 {@link OAuth2AuthorizationConsent}
     */
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "授权确认信息 不能是空的");
        Oauth2AuthorizationConsentEntity convert = consentConverter.convert(authorizationConsent);
        oauth2AuthorizationConsentRepository.save(convert);
    }

    /**
     * 删除 {@link OAuth2AuthorizationConsent}。
     *
     * @param authorizationConsent 要删除的 {@link OAuth2AuthorizationConsent}
     */
    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "授权确认信息 不能是空的");
        oauth2AuthorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    /**
     * 根据提供的 {@code registeredClientId} 和 {@code principalName} 查找并返回
     * 对应的 {@link OAuth2AuthorizationConsent}，如果未找到则返回 {@code null}。
     *
     * @param registeredClientId 注册客户端的id，对应于 {@link RegisteredClient}
     * @param principalName      主体名称，即 {@link Principal} 的名字
     * @return 如果找到了匹配的 {@link OAuth2AuthorizationConsent} 则返回它，否则返回 {@code null}
     */
    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(registeredClientId, "客户端的id 不能是空的");
        Assert.hasText(principalName, "主体名称 不能是空的");
        Oauth2AuthorizationConsentEntity entity = this.oauth2AuthorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        return consentConverter.reverse(entity);
    }

}
