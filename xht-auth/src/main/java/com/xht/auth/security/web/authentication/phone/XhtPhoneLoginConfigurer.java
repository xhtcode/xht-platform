package com.xht.auth.security.web.authentication.phone;

import com.xht.auth.constant.AuthorizationConstant;
import com.xht.auth.security.core.userdetails.XhtPostAuthenticationChecks;
import com.xht.auth.security.core.userdetails.XhtPreAuthenticationChecks;
import com.xht.auth.security.web.authentication.AbstractXhtLoginConfigurer;
import com.xht.auth.security.web.authentication.phone.filter.XhtPhoneLoginFilter;
import com.xht.auth.security.web.authentication.phone.provider.XhtPhoneLoginAuthenticationProvider;
import com.xht.framework.exception.utils.ThrowUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 登录配置
 *
 * @author xht
 **/
@Slf4j
@Setter
@SuppressWarnings("unused")
public class XhtPhoneLoginConfigurer extends AbstractXhtLoginConfigurer<XhtPhoneLoginConfigurer> {

    /**
     * 登录处理URL
     */
    private String loginProcessingUrl;

    /**
     * 表单登录手机号码字段名
     */
    private String phoneParameter = AuthorizationConstant.SPRING_SECURITY_FORM_PHONE;

    /**
     * 表单登录手机验证码字段名
     */
    private String phoneCodeParameter = AuthorizationConstant.SPRING_SECURITY_FORM_PHONE_CODE;

    /**
     * 使用指定登录处理URL构造配置器
     *
     * @param loginProcessingUrl 登录请求处理的URL地址，不能为空
     * @throws IllegalArgumentException 如果loginProcessingUrl为空时抛出异常
     */
    public XhtPhoneLoginConfigurer(String loginProcessingUrl) {
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
     */
    @Override
    public void init(HttpSecurity http) throws Exception {
        super.init(http);
        ThrowUtils.hasText(this.loginProcessingUrl, "loginProcessingUrl can not be null");
        ThrowUtils.hasText(this.phoneParameter, "phoneParameter can not be null");
        ThrowUtils.hasText(this.phoneCodeParameter, "phoneCodeParameter can not be null");
        XhtPhoneLoginAuthenticationProvider xhtPhoneLoginAuthenticationProvider = new XhtPhoneLoginAuthenticationProvider();
        xhtPhoneLoginAuthenticationProvider.setUserDetailsService(userDetailsService);
        xhtPhoneLoginAuthenticationProvider.setPreAuthenticationChecks(new XhtPreAuthenticationChecks());
        xhtPhoneLoginAuthenticationProvider.setPostAuthenticationChecks(new XhtPostAuthenticationChecks());
        http.authenticationProvider(xhtPhoneLoginAuthenticationProvider);
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
        XhtPhoneLoginFilter phoneLoginFilter = new XhtPhoneLoginFilter(this.loginProcessingUrl);
        phoneLoginFilter.setPhoneParameter(phoneParameter);
        phoneLoginFilter.setPhoneCodeParameter(phoneCodeParameter);
        authenticationFilterAddInformation(http, phoneLoginFilter);
        http.addFilterBefore(postProcess(phoneLoginFilter), UsernamePasswordAuthenticationFilter.class);
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
    public static XhtPhoneLoginConfigurer phoneLogin(String loginProcessingUrl) {
        return new XhtPhoneLoginConfigurer(loginProcessingUrl);
    }

}
