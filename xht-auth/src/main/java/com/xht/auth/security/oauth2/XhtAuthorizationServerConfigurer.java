package com.xht.auth.security.oauth2;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.security.oauth2.server.authorization.password.PassWordAuthenticationConverter;
import com.xht.auth.security.oauth2.server.authorization.password.PassWordAuthenticationProvider;
import com.xht.auth.security.oauth2.server.authorization.phone.PhoneAuthenticationConverter;
import com.xht.auth.security.oauth2.server.authorization.phone.PhoneAuthenticationProvider;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

/**
 * 描述：自定义
 * OAuth2.0 授权服务器配置
 *
 * @author xht
 **/
@Slf4j
@Setter
public final class XhtAuthorizationServerConfigurer extends AbstractHttpConfigurer<XhtAuthorizationServerConfigurer, HttpSecurity> {

    private BasicUserDetailsService userDetailsService;

    private ICaptchaService captchaService;

    private final OAuth2AuthorizationServerConfigurer authorizationServerConfigurer;

    public XhtAuthorizationServerConfigurer(OAuth2AuthorizationServerConfigurer authorizationServerConfigurer) {
        Assert.notNull(authorizationServerConfigurer, "authorizationServerConfigurer cannot be null");
        this.authorizationServerConfigurer = authorizationServerConfigurer;
    }

    /**
     * 初始化HTTP安全配置
     * <p>
     * 该方法在配置阶段被调用，用于注册和配置表单登录认证提供者。
     * 创建并配置XhtFormLoginAuthenticationProvider，设置用户详情服务和认证检查器，
     * 然后将其注册到HttpSecurity中。
     * </p>
     *
     * @param http HTTP安全配置对象，用于配置各种安全相关的组件
     */
    @Override
    public void init(HttpSecurity http) {
        log.info("XhtAuthorizationServerConfigurer init");
        Assert.notNull(userDetailsService, "basicUserDetailsService cannot be null");
        Assert.notNull(captchaService, "iCaptchaService cannot be null");
        authorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> tokenEndpoint.accessTokenRequestConverters((converters) -> {
            converters.add(new PassWordAuthenticationConverter());
            converters.add(new PhoneAuthenticationConverter());
        }));
    }

    /**
     * 配置HTTP安全过滤器链
     * <p>
     * 该方法在初始化阶段之后被调用，用于创建和配置表单登录过滤器。
     * 设置过滤器的各项属性，包括参数名、验证码服务、认证管理器、
     * 成功/失败处理器、会话策略、记住我服务等，并将过滤器添加到过滤器链中。
     * </p>
     *
     * @param http HTTP安全配置对象，用于获取共享对象和配置过滤器链
     */
    @Override
    public void configure(HttpSecurity http) {
        log.info("XhtAuthorizationServerConfigurer configure");
        OAuth2TokenGenerator<?> tokenGenerator = http.getSharedObject(OAuth2TokenGenerator.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        PassWordAuthenticationProvider passWordAuthenticationProvider = new PassWordAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, captchaService);
        PhoneAuthenticationProvider phoneAuthenticationProvider = new PhoneAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, captchaService);
        http.authenticationProvider(passWordAuthenticationProvider);
        http.authenticationProvider(phoneAuthenticationProvider);
    }


    /**
     * 获取授权服务器配置器实例
     * @return 授权服务器配置器实例
     */
    public static XhtAuthorizationServerConfigurer authorizationServer(OAuth2AuthorizationServerConfigurer serverConfigurer) {
        return new XhtAuthorizationServerConfigurer(serverConfigurer);
    }
}
