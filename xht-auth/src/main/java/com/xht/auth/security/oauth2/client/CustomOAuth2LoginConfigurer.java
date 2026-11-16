package com.xht.auth.security.oauth2.client;

import com.xht.framework.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;

/**
 * 描述：oauth2 都三方登录配置
 *
 * @author xht
 **/
@Slf4j
public class CustomOAuth2LoginConfigurer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {

    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity> oauth2Login) {
        oauth2Login.defaultSuccessUrl("http://www.xht.sso.com:5000/home", true);
        oauth2Login.failureHandler((request, response, exception) -> {
            ServletUtils.writeJson(response, exception.getMessage());
        });
    }

}
