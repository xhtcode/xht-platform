package com.xht.framework.openfeign.configurers;

import com.xht.framework.openfeign.interceptor.FeignAuthenticationInterceptor;
import com.xht.framework.openfeign.interceptor.FeignTokenInterceptor;
import com.xht.framework.openfeign.interceptor.FeignTraceLogInterceptor;
import com.xht.framework.core.properties.SecurityHeaderProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * openfeign 配置类
 *
 * @author xht
 **/
@Slf4j
@Configuration
public class BasicOpenFeignAutoConfig {

    /**
     * feign authentication 请求拦截器
     *
     * @param securityHeaderProperties securityHeaderProperties
     * @return FeignAuthenticationInterceptor
     */
    @Bean
    public FeignAuthenticationInterceptor feignAuthenticationInterceptor(SecurityHeaderProperties securityHeaderProperties) {
        return new FeignAuthenticationInterceptor(securityHeaderProperties);
    }

    /**
     * feign token 请求拦截器
     *
     * @param securityHeaderProperties securityHeaderProperties
     * @return FeignTokenInterceptor
     */
    @Bean
    public FeignTokenInterceptor feignTokenInterceptor(SecurityHeaderProperties securityHeaderProperties) {
        return new FeignTokenInterceptor(securityHeaderProperties);
    }


    /**
     * feign trace log 请求拦截器
     *
     * @param securityHeaderProperties securityHeaderProperties
     * @return FeignTraceLogInterceptor
     */
    @Bean
    public FeignTraceLogInterceptor feignRequestInterceptor(SecurityHeaderProperties securityHeaderProperties) {
        return new FeignTraceLogInterceptor(securityHeaderProperties);
    }


}
