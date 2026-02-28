package com.xht.boot.security;

import com.xht.boot.security.context.SecurityUserContextServiceImpl;
import com.xht.framework.core.context.UserContextService;
import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.mybatis.mapper.common.UtilsMapper;
import com.xht.framework.security.aspect.IgnoreAuthAspect;
import com.xht.framework.security.crypto.password.BasicPasswordEncoder;
import com.xht.framework.security.handler.SecurityExceptionHandler;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import com.xht.framework.security.properties.TokenLightningCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnClass(PermitAllUrlProperties.class)
@EnableConfigurationProperties({TokenLightningCacheProperties.class})
public class SecurityAutoConfiguration {

    public SecurityAutoConfiguration() {
        log.debug("[xht] |- xht-boot-security 启动成功！");
    }

    /**
     * 密码加密器
     *
     * @return 密码加密器
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BasicPasswordEncoder();
    }

    /**
     * 切面配置
     *
     * @param securityHeaderProperties {@link SecurityHeaderProperties}
     * @return 配置切面
     */
    @Bean
    public IgnoreAuthAspect ignoreAuthAspect(SecurityHeaderProperties securityHeaderProperties) {
        return new IgnoreAuthAspect(securityHeaderProperties);
    }

    /**
     * 安全异常处理器
     *
     * @return 安全异常处理器
     */
    @Bean
    public SecurityExceptionHandler securityExceptionHandler() {
        return new SecurityExceptionHandler();
    }

    /**
     * 用户上下文服务接口实现
     *
     * @return 用户上下文服务接口实现
     */
    @Bean
    @ConditionalOnBean(UtilsMapper.class)
    public UserContextService securityUserContextServiceImpl() {
        return new SecurityUserContextServiceImpl();
    }

}
