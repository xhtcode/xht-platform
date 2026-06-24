package com.xht.framework.security.core.device;

import com.xht.framework.security.core.device.filter.DeviceCodeFilter;
import com.xht.framework.security.core.device.provider.DeviceCodeProvider;
import com.xht.framework.exception.utils.ThrowUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

/**
 * 描述： 设备验证码配置器
 *
 * @author xht
 **/
@Slf4j
@Setter
public class DeviceCodeConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private DeviceCodeProvider deviceCodeProvider;

    private String deviceCodeName;

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
        ThrowUtils.notNull(deviceCodeProvider, "deviceCodeProvider cannot be null");
        ThrowUtils.hasText(deviceCodeName, "deviceCodeName can not be null");
    }

    /**
     * 配置HTTP安全过滤器链
     * <p>
     * 该方法在初始化阶段之后被调用，用于创建和配置表单登录过滤器。
     * 设置过滤器的各项属性，包括参数名、验证码服务、认证管理器、
     * 成功/失败处理器、会话策略、记住我服务等，并将过滤器添加到过滤器链中。
     * </p>
     *
     * @param httpSecurity HTTP安全配置对象，用于获取共享对象和配置过滤器链
     */
    @Override
    public void configure(HttpSecurity httpSecurity) {
        DeviceCodeFilter deviceCodeFilter = new DeviceCodeFilter();
        deviceCodeFilter.setDeviceCodeProvider(deviceCodeProvider);
        deviceCodeFilter.setDeviceCodeName(deviceCodeName);
        httpSecurity.addFilterBefore(deviceCodeFilter, SecurityContextHolderFilter.class);
    }

    /**
     * 创建设备验证码配置器实例
     *
     * @return 新的DeviceCodeConfigurer实例
     */
    public static DeviceCodeConfigurer deviceCode() {
        return new DeviceCodeConfigurer();
    }

}
