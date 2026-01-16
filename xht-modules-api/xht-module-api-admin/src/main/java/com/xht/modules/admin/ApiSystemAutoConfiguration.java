package com.xht.modules.admin;

import com.xht.modules.admin.listener.BLogApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@ComponentScan(value = "com.xht.modules.admin.*.api.factory")
@AutoConfiguration
@EnableFeignClients(value = "com.xht.modules.admin.*.api")
public class ApiSystemAutoConfiguration {

    /**
     * feign 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    @ConditionalOnProperty(value = "xht.blog.repository-type", havingValue = "feign")
    public BLogApplicationListener feignBLogApplicationListener() {
        return new BLogApplicationListener();
    }

}
