package com.xht.framework.security;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.security.aspect.IgnoreAuthAspect;
import com.xht.framework.security.crypto.password.BasicPasswordEncoder;
import com.xht.framework.security.handler.SecurityExceptionHandler;
import com.xht.framework.security.properties.SecurityProperties;
import com.xht.framework.security.properties.TokenLightningCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({SecurityProperties.class, TokenLightningCacheProperties.class})
public class SecurityAutoConfiguration {

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

}
