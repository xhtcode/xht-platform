package com.xht.framework.security.configurers;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.crypto.password.BasicPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

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


}
