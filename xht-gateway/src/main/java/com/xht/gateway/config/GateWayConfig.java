package com.xht.gateway.config;

import com.xht.gateway.config.properties.GatewayGlobalProperties;
import com.xht.gateway.filter.GlobalRequestFilter;
import com.xht.gateway.handler.GatewayGlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * gateway网关配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class GateWayConfig {

    /**
     * 全局异常处理
     *
     * @return GatewayGlobalExceptionHandler
     */
    @Bean
    public GatewayGlobalExceptionHandler globalExceptionHandler() {
        return new GatewayGlobalExceptionHandler();
    }

    /**
     * 全局请求拦截器
     * @param gatewayGlobalProperties 网关全局配置
     * @return GlobalRequestFilter
     */
    @Bean
    public GlobalRequestFilter gatewayFilterRegistration(GatewayGlobalProperties gatewayGlobalProperties) {
        return new GlobalRequestFilter(gatewayGlobalProperties);
    }
}
