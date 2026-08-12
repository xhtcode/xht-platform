package com.xht.platform.blog;

import com.xht.framework.log.annotations.ConditionalOnBLog;
import com.xht.framework.log.configurers.BLogProperties;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.platform.blog.repository.BLogRepositoryImpl;
import com.xht.platform.common.audit.api.IBLogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@ComponentScan(value = "com.xht.platform.common.audit.api.factory")
@EnableFeignClients(clients = IBLogClient.class)
public class BLogAutoConfiguration {

    public BLogAutoConfiguration() {
        log.debug("系统日志自动配置启动");
    }

    /**
     * feign 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    @ConditionalOnBLog(value = BLogProperties.RepositoryType.FEIGN)
    public BLogRepository bLogRepository() {
        return new BLogRepositoryImpl();
    }

}
