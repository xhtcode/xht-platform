package com.xht.cloud.openfeign;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.openfeign.interceptor.FeignIgnoreAuthInterceptor;
import com.xht.framework.openfeign.interceptor.FeignTokenInterceptor;
import com.xht.framework.openfeign.interceptor.FeignTraceLogInterceptor;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * openfeign 配置类
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Feign.class)
public class BasicOpenFeignAutoConfig {

    /**
     * feign authentication 请求拦截器
     *
     * @param securityHeaderProperties securityHeaderProperties
     * @return FeignIgnoreAuthInterceptor
     */
    @Bean
    public FeignIgnoreAuthInterceptor feignIgnoreAuthInterceptor(SecurityHeaderProperties securityHeaderProperties) {
        return new FeignIgnoreAuthInterceptor(securityHeaderProperties);
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
     * @return FeignTraceLogInterceptor
     */
    @Bean
    public FeignTraceLogInterceptor feignRequestInterceptor() {
        return new FeignTraceLogInterceptor();
    }

}
