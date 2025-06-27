package com.xht.boot.oauth2.repository;


import com.xht.boot.oauth2.entity.OAuth2UserConsent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2UserConsentRepository extends CrudRepository<OAuth2UserConsent, String> {

    OAuth2UserConsent findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

}