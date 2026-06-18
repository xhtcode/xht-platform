package com.xht.auth.security.oauth2.client;

import com.xht.auth.security.oauth2.client.userinfo.XhtDefaultOAuth2UserService;
import com.xht.framework.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

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
        oauth2Login.failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                exception.printStackTrace();
                ServletUtil.writeJson(response, exception.getMessage());
            }
        });
    }

}
