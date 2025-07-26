package com.xht.framework.security.configurers;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.security.aspect.InnerAuthAspect;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.crypto.password.BasicPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xht
 **/
@Slf4j
@Configuration
public class SecurityAutoConfigurer {

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
     * 用户详情服务
     *
     * @return 用户详情服务
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new BasicUserDetailsService();
    }

    /**
     * 切面配置
     * @param securityHeaderProperties {@link SecurityHeaderProperties}
     * @return 配置切面
     */
    @Bean
    public InnerAuthAspect innerAuthAspect(SecurityHeaderProperties securityHeaderProperties) {
        return new InnerAuthAspect(securityHeaderProperties);
    }
}
