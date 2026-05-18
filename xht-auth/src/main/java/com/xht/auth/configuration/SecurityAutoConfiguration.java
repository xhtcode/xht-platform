package com.xht.auth.configuration;

import com.xht.auth.authentication.dao.CustomAuthenticationProvider;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.configuration.properties.RememberMeProperties;
import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.auth.security.web.authentication.AuthorizationServerFailureHandler;
import com.xht.auth.security.web.authentication.AuthorizationServerSuccessHandler;
import com.xht.auth.security.web.authentication.form.XhtFormLoginConfigurer;
import com.xht.auth.security.web.authentication.logout.XhtLogoutSuccessHandler;
import com.xht.auth.security.web.authentication.phone.XhtPhoneLoginConfigurer;
import com.xht.auth.security.web.authentication.qr.XhtQrCodeLoginConfigurer;
import com.xht.auth.security.web.authentication.qr.manager.AbstractQrManager;
import com.xht.auth.security.web.authentication.session.XhtSessionLimit;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.security.configurers.CustomAuthorizeHttpRequestsConfigurer;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import com.xht.framework.security.web.access.Http401AccessDeniedHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * 描述: spring security 自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityAutoConfiguration {

    private final BasicUserDetailsService basicUserDetailsService;

    private final XhtOauth2Properties xhtOauth2Properties;

    private final PermitAllUrlProperties permitAllUrlProperties;

    private final ResourceAuthenticationEntryPoint resourceAuthenticationEntryPoint;

    private final ResourceBearerTokenResolver resourceBearerTokenResolver;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    private final PersistentTokenRepository persistentTokenRepository;

    private final RememberMeProperties rememberMeProperties;

    private final AbstractQrManager qrDataManager;

    private final ICaptchaService iCaptchaService;

    /**
     * 配置默认的安全过滤器链
     * <p>
     * 该方法是Spring Security的核心配置，设置了以下安全策略：
     *
     * @param http HttpSecurity对象，用于配置Web安全设置
     * @return 构建完成的SecurityFilterChain实例
     * @throws Exception 当配置过程中出现错误时抛出异常
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        XhtOauth2Properties.AuthorizationServer authorizationServer = xhtOauth2Properties.getAuthorizationServer();
        AuthenticationSuccessHandler successHandler = new AuthorizationServerSuccessHandler();
        AuthenticationFailureHandler failureHandler = new AuthorizationServerFailureHandler();
        CustomAuthorizeHttpRequestsConfigurer requestsConfigurer = new CustomAuthorizeHttpRequestsConfigurer(permitAllUrlProperties);
        // 开启CORS配置，配合下边的CorsConfigurationSource配置实现跨域配置
        http.cors(Customizer.withDefaults());
        // 禁用csrf
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(requestsConfigurer);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(configurer -> {
            configurer.opaqueToken(opaqueToken -> opaqueToken.introspector(opaqueTokenIntrospector));
            configurer.authenticationEntryPoint(resourceAuthenticationEntryPoint);
            configurer.bearerTokenResolver(resourceBearerTokenResolver);
        });
        http.exceptionHandling(handlingConfigurer -> {
            handlingConfigurer.accessDeniedHandler(new Http401AccessDeniedHandler());// 请求未授权的接口
            handlingConfigurer.authenticationEntryPoint(new ResourceAuthenticationEntryPoint());
        });
        http.authenticationProvider(new CustomAuthenticationProvider(basicUserDetailsService));
        http.logout(logoutConfigurer -> {
            logoutConfigurer.logoutUrl("/oauth2/logout");
            logoutConfigurer.deleteCookies("JSESSIONID");
            logoutConfigurer.invalidateHttpSession(true);
            logoutConfigurer.clearAuthentication(true);
            logoutConfigurer.logoutSuccessHandler(new XhtLogoutSuccessHandler());
        });
        http.sessionManagement(sessionConfigurer -> {
            sessionConfigurer.sessionFixation().changeSessionId();
            sessionConfigurer.sessionConcurrency(configurer -> {
                configurer.maxSessionsPreventsLogin(false);// 设置false 新登录踢掉旧登陆
                configurer.maximumSessions(new XhtSessionLimit());
                configurer.expiredSessionStrategy(event -> {
                    HttpServletResponse response = event.getResponse();
                    ServletUtil.writeJson(response, R.error().msg("你的账号在异地登录，请重新登录！").build());
                });
            });
        });
        http.rememberMe(rememberMeConfigurer -> {
            rememberMeConfigurer.rememberMeParameter(rememberMeProperties.getParameter());
            rememberMeConfigurer.rememberMeCookieName(rememberMeProperties.getCookieName());
            rememberMeConfigurer.tokenValiditySeconds(rememberMeProperties.getTokenValiditySeconds().intValue());
            rememberMeConfigurer.userDetailsService(basicUserDetailsService);
            rememberMeConfigurer.tokenRepository(persistentTokenRepository);
        });
        http.with(XhtQrCodeLoginConfigurer.qrCodeLogin(), configurer -> {
            configurer.setQrDataManager(qrDataManager);
            configurer.setSuccessHandler(successHandler);
            configurer.setFailureHandler(failureHandler);
        });
        http.with(XhtFormLoginConfigurer.formLogin(authorizationServer.getLoginPage()), configurer -> {
            configurer.setLoginSuccessHandler(successHandler);
            configurer.setLoginFailureHandler(failureHandler);
            configurer.setUserDetailsService(basicUserDetailsService);
            configurer.setICaptchaService(iCaptchaService);
        });
        http.with(XhtPhoneLoginConfigurer.phoneLogin(), configurer -> {
            configurer.setLoginSuccessHandler(successHandler);
            configurer.setLoginFailureHandler(failureHandler);
            configurer.setUserDetailsService(basicUserDetailsService);
            configurer.setICaptchaService(iCaptchaService);
        });
        return http.build();
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
