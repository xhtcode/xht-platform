package com.xht.boot.oauth2.service;


import com.xht.boot.oauth2.entity.OAuth2UserConsent;
import com.xht.boot.oauth2.repository.OAuth2UserConsentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

@Slf4j
public class RedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final OAuth2UserConsentRepository userConsentRepository;

    public RedisOAuth2AuthorizationConsentService(OAuth2UserConsentRepository userConsentRepository) {
        Assert.notNull(userConsentRepository, "userConsentRepository cannot be null");
        this.userConsentRepository = userConsentRepository;
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        OAuth2UserConsent oauth2UserConsent = ModelMapper.convertOAuth2UserConsent(authorizationConsent);
        this.userConsentRepository.save(oauth2UserConsent);
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        this.userConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Nullable
    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        OAuth2UserConsent oauth2UserConsent = this.userConsentRepository
                .findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName);
        return oauth2UserConsent != null ? ModelMapper.convertOAuth2AuthorizationConsent(oauth2UserConsent) : null;
    }

}