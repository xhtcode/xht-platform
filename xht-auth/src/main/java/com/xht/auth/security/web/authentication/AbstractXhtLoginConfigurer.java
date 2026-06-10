package com.xht.auth.security.web.authentication;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.exception.utils.ThrowUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;

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

    /**
     * 初始化HTTP安全配置
     * <p>
     * 该方法在配置阶段被调用，用于注册和配置表单登录认证提供者。
     * 创建并配置XhtFormLoginAuthenticationProvider，设置用户详情服务和认证检查器，
     * 然后将其注册到HttpSecurity中。
     * </p>
     *
     * @param http HTTP安全配置对象，用于配置各种安全相关的组件
     * @throws IllegalStateException 如果必要的配置参数为空时抛出异常
     */
    @Override
    public void init(HttpSecurity http) throws Exception {
        ThrowUtils.notNull(loginSuccessHandler, "loginSuccessHandler cannot be null");
        ThrowUtils.notNull(loginFailureHandler, "loginFailureHandler cannot be null");
        ThrowUtils.notNull(userDetailsService, "userDetailsService cannot be null");
        ThrowUtils.notNull(iCaptchaService, "iCaptchaService cannot be null");
    }

    /**
     * 为认证过滤器添加配置信息
     * <p>
     * 该方法用于配置AbstractXhtAuthenticationFilter的各种依赖组件，包括：
     * 验证码服务、认证管理器、认证成功/失败处理器、会话认证策略、
     * RememberMe服务以及安全上下文仓库等。
     * 所有从HttpSecurity中获取的共享对象如果存在则进行设置，确保过滤器具备完整的认证能力。
     * </p>
     *
     * @param http HttpSecurity对象，用于获取Spring Security的共享组件
     * @param authenticationFilter 需要配置的认证过滤器实例
     */
    protected final <E extends AbstractXhtAuthenticationToken> void authenticationFilterAddInformation(HttpSecurity http, AbstractXhtAuthenticationFilter<E> authenticationFilter) {
        authenticationFilter.setICaptchaService(iCaptchaService);
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            authenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            authenticationFilter.setRememberMeServices(rememberMeServices);
        }
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository != null) {
            authenticationFilter.setSecurityContextRepository(securityContextRepository);
        }
        authenticationFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
    }

}
