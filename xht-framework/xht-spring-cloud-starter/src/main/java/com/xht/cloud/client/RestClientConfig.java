package com.xht.cloud.client;

import com.xht.framework.core.constant.HttpConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestClient;

/**
 * RestClient 配置类
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class RestClientConfig {

    public RestClientConfig() {
        log.info("【xht-spring-cloud-starter】初始化 RestClient 配置类");
    }

    @Bean
    @Order
    @LoadBalanced
    @ConditionalOnMissingBean
    public RestClient loadBalancedRestClientBuilder() {
        return RestClient
                .builder()
                .defaultHeader(HttpConstants.Header.CONTENT_TYPE.getValue(), HttpConstants.ContentType.APPLICATION_JSON_UTF8_VALUE.getValue())
                .build();
    }

}
