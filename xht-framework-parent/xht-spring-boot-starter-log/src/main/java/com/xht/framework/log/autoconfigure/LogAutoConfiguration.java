package com.xht.framework.log.autoconfigure;

import com.xht.framework.log.aspect.BLogAspect;
import com.xht.framework.log.aspect.MethodTimeAspect;
import com.xht.framework.log.repository.BLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动装配
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

    public LogAutoConfiguration() {
        log.debug("【xht-spring-boot-starter-log】 启动成功！");
    }

    /**
     * 日志切面
     *
     * @return {@link BLogAspect}
     */
    @Bean
    @ConditionalOnBean(BLogRepository.class)
    public BLogAspect logAspect(BLogRepository bLogRepository) {
        return new BLogAspect(bLogRepository);
    }

    /**
     * 方法执行时间统计切面
     *
     * @return {@link MethodTimeAspect} 方法计时切面实例
     */
    @Bean
    public MethodTimeAspect methodTimeAspect() {
        return new MethodTimeAspect();
    }

}