package com.xht.auth.security.web.authentication;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.Setter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 登录配置
 *
 * @author xht
 **/
@Setter
public abstract class AbstractXhtLoginConfigurer<T extends AbstractXhtLoginConfigurer<T>> extends AbstractHttpConfigurer<T, HttpSecurity> {


    protected AuthenticationSuccessHandler loginSuccessHandler;

    protected AuthenticationFailureHandler loginFailureHandler;

    protected BasicUserDetailsService userDetailsService;

    protected ICaptchaService iCaptchaService;

}
