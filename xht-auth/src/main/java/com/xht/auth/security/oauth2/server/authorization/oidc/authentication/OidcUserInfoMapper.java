package com.xht.auth.security.oauth2.server.authorization.oidc.authentication;

import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * OidcUserInfoMapper 描述
 *
 * @author xht
 **/
@Slf4j
public class OidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext context) {
        OidcUserInfo.Builder builder = OidcUserInfo.builder();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        builder.subject(username).name(username);
        Set<String> scope = Optional.ofNullable(context.getAccessToken()).map(OAuth2AccessToken::getScopes).orElse(Collections.emptySet());
        Object principal = Optional.ofNullable(context.getAuthorization()).map(OAuth2Authorization::getAttributes)
                .map(item -> item.get(Principal.class.getName()))
                .orElse(null);
        if (principal instanceof BasicUserDetails userDetails) {
            builder.nickname(userDetails.getNickName());
            builder.claim("userType", userDetails.getUserType());
            builder.claim("userStatus", userDetails.getUserStatus());
            if (scope.contains("role")) {
                builder.claim("roles", userDetails.getRoleCodes());
            }
            if (scope.contains("menu")) {
                builder.claim("menus", userDetails.getMenuButtonCodes());
            }
            if (scope.contains("phone")) {
                builder.phoneNumber(userDetails.getUserPhone());
            }
        }
        return builder.build();
    }


}

