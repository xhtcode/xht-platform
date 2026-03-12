package com.xht.auth.configuration;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientBuilder 配置类
 *
 * @author xht
 **/
@Configuration
@RequiredArgsConstructor
public class WebClientBuilderConfig {

    private final SecurityHeaderProperties securityHeaderProperties;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient
                .builder()
                .defaultHeader(securityHeaderProperties.getAuthKey(), securityHeaderProperties.getAuthValue())
                .defaultHeader("Content-Type", "application/json")
                ;
    }

}
