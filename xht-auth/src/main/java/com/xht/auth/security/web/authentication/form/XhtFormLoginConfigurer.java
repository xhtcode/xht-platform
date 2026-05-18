package com.xht.auth.security.web.authentication.form;

import com.xht.auth.constant.AuthorizationConstant;
import com.xht.auth.security.core.userdetails.XhtPostAuthenticationChecks;
import com.xht.auth.security.core.userdetails.XhtPreAuthenticationChecks;
import com.xht.auth.security.web.authentication.AbstractXhtLoginConfigurer;
import com.xht.auth.security.web.authentication.form.filter.XhtFormLoginFilter;
import com.xht.auth.security.web.authentication.form.provider.XhtFormLoginAuthenticationProvider;
import com.xht.framework.core.exception.utils.ThrowUtils;
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
@SuppressWarnings("unused")
public class XhtFormLoginConfigurer extends AbstractXhtLoginConfigurer<XhtFormLoginConfigurer> {

    /**
     * 登录处理URL
     */
    private String loginProcessingUrl;

    /**
     * 用户名字段参数名
     */
    private String usernameParameter = AuthorizationConstant.SPRING_SECURITY_FORM_USERNAME;

    /**
     * 密码字段参数名
     */
    private String passwordParameter = AuthorizationConstant.SPRING_SECURITY_FORM_PASSWORD;

    /**
     * 验证码键字段参数名
     */
    private String captchaKeyParameter = AuthorizationConstant.SPRING_SECURITY_FORM_CAPTCHA_KEY;

    /**
     * 验证码值字段参数名
     */
    private String captchaCodeParameter = AuthorizationConstant.SPRING_SECURITY_FORM_CAPTCHA_CODE;

    /**
     * 登录请求处理URL
     */
    public XhtFormLoginConfigurer() {
        this.loginProcessingUrl = "/sso/login";
    }

    /**
     * 使用指定登录处理URL构造配置器
     *
     * @param loginProcessingUrl 登录请求处理的URL地址，不能为空
     * @throws IllegalArgumentException 如果loginProcessingUrl为空时抛出异常
     */
    public XhtFormLoginConfigurer(String loginProcessingUrl) {
        ThrowUtils.hasText(loginProcessingUrl, "loginProcessingUrl can not be null");
        this.loginProcessingUrl = loginProcessingUrl;
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
     * @throws IllegalStateException 如果必要的配置参数为空时抛出异常
     */
    @Override
    public void init(HttpSecurity http) {
        log.debug("XhtLoginConfigurer init");
        ThrowUtils.hasText(this.loginProcessingUrl, "loginProcessingUrl can not be null");
        ThrowUtils.hasText(this.usernameParameter, "usernameParameter can not be null");
        ThrowUtils.hasText(this.passwordParameter, "passwordParameter can not be null");
        ThrowUtils.hasText(this.captchaKeyParameter, "captchaKeyParameter can not be null");
        ThrowUtils.hasText(this.captchaCodeParameter, "captchaCodeParameter can not be null");
        XhtFormLoginAuthenticationProvider xhtFormLoginAuthenticationProvider = new XhtFormLoginAuthenticationProvider();
        xhtFormLoginAuthenticationProvider.setUserDetailsService(userDetailsService);
        xhtFormLoginAuthenticationProvider.setPreAuthenticationChecks(new XhtPreAuthenticationChecks());
        xhtFormLoginAuthenticationProvider.setPostAuthenticationChecks(new XhtPostAuthenticationChecks());
        http.authenticationProvider(xhtFormLoginAuthenticationProvider);
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
        log.debug("XhtLoginConfigurer configure");
        XhtFormLoginFilter formLoginFilter = new XhtFormLoginFilter("/sso/unLogin");
        formLoginFilter.setUsernameParameter(usernameParameter);
        formLoginFilter.setPasswordParameter(passwordParameter);
        formLoginFilter.setCaptchaKeyParameter(captchaKeyParameter);
        formLoginFilter.setCaptchaCodeParameter(captchaCodeParameter);
        formLoginFilter.setICaptchaService(iCaptchaService);
        formLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        formLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        formLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            formLoginFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            formLoginFilter.setRememberMeServices(rememberMeServices);
        }
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository != null) {
            formLoginFilter.setSecurityContextRepository(securityContextRepository);
        }
        formLoginFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
        http.addFilterBefore(postProcess(formLoginFilter), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 创建表单登录配置器实例
     * <p>
     * 使用默认的登录处理URL创建配置器
     * </p>
     *
     * @return 新的XhtFormLoginConfigurer实例
     */
    public static XhtFormLoginConfigurer formLogin() {
        return new XhtFormLoginConfigurer();
    }

    /**
     * 创建表单登录配置器实例
     * <p>
     * 使用指定的登录处理URL创建配置器
     * </p>
     *
     * @param loginProcessingUrl 登录请求处理的URL地址
     * @return 新的XhtFormLoginConfigurer实例
     */
    public static XhtFormLoginConfigurer formLogin(String loginProcessingUrl) {
        return new XhtFormLoginConfigurer(loginProcessingUrl);
    }

}
