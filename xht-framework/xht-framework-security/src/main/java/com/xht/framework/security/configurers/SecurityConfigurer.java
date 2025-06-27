package com.xht.framework.security.configurers;

import com.xht.framework.security.properties.PermitAllUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.xht.framework.security.constant.SecurityConstant.RESOURCE_SERVER_BEAN_NAME;

/**
 * SecurityConfigurer
 *
 * @author xht
 **/
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@ConditionalOnMissingBean(name = RESOURCE_SERVER_BEAN_NAME)
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class SecurityConfigurer {

    private final PermitAllUrlProperties permitAllUrlProperties;

    public SecurityConfigurer(PermitAllUrlProperties permitAllUrlProperties) {
        this.permitAllUrlProperties = permitAllUrlProperties;
        log.info(">>>>>>SecurityConfigurer init<<<<<<");
    }

    /**
     * spring security 配置
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthorizeHttpRequestsConfigurer requestsConfigurer = new CustomAuthorizeHttpRequestsConfigurer(permitAllUrlProperties);
        // @formatter:off
        http
                .authorizeHttpRequests(requestsConfigurer)
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(Customizer.withDefaults())
        ;
        // @formatter:on
        return http.build();
    }

}
