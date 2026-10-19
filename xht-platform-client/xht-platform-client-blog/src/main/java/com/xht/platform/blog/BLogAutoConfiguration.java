package com.xht.platform.blog;

import com.xht.framework.log.annotations.ConditionalOnBLog;
import com.xht.framework.log.configurers.BLogProperties;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.platform.blog.client.BLogFeignClient;
import com.xht.platform.blog.client.factory.BLogClientFallbackFactory;
import com.xht.platform.blog.repository.BLogRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = BLogFeignClient.class)
public class BLogAutoConfiguration {

    public BLogAutoConfiguration() {
        log.debug("系统日志自动配置启动");
    }

    /**
     * 日志保存降级工厂
     * <p>
     * 显式注册而非 {@code @Component}，避免把降级的装配责任推给调用方的组件扫描。
     *
     * @return 降级工厂
     */
    @Bean
    public BLogClientFallbackFactory bLogClientFallbackFactory() {
        return new BLogClientFallbackFactory();
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
