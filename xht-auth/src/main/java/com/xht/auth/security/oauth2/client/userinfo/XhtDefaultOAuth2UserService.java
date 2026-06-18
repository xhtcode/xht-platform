package com.xht.auth.security.oauth2.client.userinfo;

import com.xht.auth.security.oauth2.client.converter.Oauth2UserConverterContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * 描述：默认oauth2 
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class XhtDefaultOAuth2UserService extends DefaultOAuth2UserService {

    private final Oauth2UserConverterContext oauth2UserConverterContext;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return oauth2UserConverterContext.execute(userRequest, oAuth2User);
    }
}
