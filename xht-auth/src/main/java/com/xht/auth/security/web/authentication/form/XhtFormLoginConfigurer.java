package com.xht.auth.security.web.authentication.form;

import com.xht.auth.security.core.userdetails.XhtPostAuthenticationChecks;
import com.xht.auth.security.core.userdetails.XhtPreAuthenticationChecks;
import com.xht.auth.security.web.authentication.AbstractXhtLoginConfigurer;
import com.xht.auth.security.web.authentication.XhtLoginAuthenticationProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * 登录配置
 *
 * @author xht
 **/
@Slf4j
@Setter
public class XhtFormLoginConfigurer extends AbstractXhtLoginConfigurer<XhtFormLoginConfigurer> {


    @Override
    public void init(HttpSecurity http) throws Exception {
        log.debug("XhtLoginConfigurer init");
        XhtLoginAuthenticationProvider xhtLoginAuthenticationProvider = new XhtLoginAuthenticationProvider();
        xhtLoginAuthenticationProvider.setUserDetailsService(userDetailsService);
        xhtLoginAuthenticationProvider.setPreAuthenticationChecks(new XhtPreAuthenticationChecks());
        xhtLoginAuthenticationProvider.setPostAuthenticationChecks(new XhtPostAuthenticationChecks());
        http.authenticationProvider(xhtLoginAuthenticationProvider);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.debug("XhtLoginConfigurer configure");
        XhtFormLoginFilter xhtFormLoginFilter = new XhtFormLoginFilter("/sso/unLogin");
        xhtFormLoginFilter.setICaptchaService(iCaptchaService);
        xhtFormLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        xhtFormLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        xhtFormLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            xhtFormLoginFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            xhtFormLoginFilter.setRememberMeServices(rememberMeServices);
        }
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository != null) {
            xhtFormLoginFilter.setSecurityContextRepository(securityContextRepository);
        }
        xhtFormLoginFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
        XhtFormLoginFilter filter = postProcess(xhtFormLoginFilter);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public static XhtFormLoginConfigurer formLogin() {
        return new XhtFormLoginConfigurer();
    }

}
