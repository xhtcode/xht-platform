package com.xht.boot.log;

import com.xht.framework.log.aspect.BLogAspect;
import com.xht.framework.log.aspect.MethodTimeAspect;
import com.xht.framework.log.repository.BLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
        log.debug("[xht] |- xht-boot-log 启动成功！");
    }

    /**
     * 日志切面
     *
     * @return {@link BLogAspect}
     */
    @Bean
    @ConditionalOnClass(BLogRepository.class)
    public BLogAspect logAspect() {
        return new BLogAspect();
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