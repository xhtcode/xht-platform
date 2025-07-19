package com.xht.framework.log.configurers;

import com.xht.framework.log.aspect.LogAspect;
import com.xht.framework.log.properties.BasicLogProperties;
import com.xht.framework.log.repository.LogRepository;
import com.xht.framework.log.repository.impl.DefaultLogRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 日志自动装配
 *
 * @author xht
 **/
@Slf4j
@Configuration
@SuppressWarnings("all")
@RequiredArgsConstructor
@EnableConfigurationProperties(BasicLogProperties.class)
public class LogAutoConfigurer {

    /**
     * 日志切面
     *
     * @param logRepository 日志存储
     * @return {@link LogAspect}
     */
    @Bean
    public LogAspect logAspect(LogRepository logRepository, BasicLogProperties basicLogProperties) {
        return new LogAspect(logRepository, basicLogProperties);
    }

    /**
     * 默认的日志存储
     *
     * @return {@link LogRepository}
     */
    @Bean
    @ConditionalOnMissingBean(LogRepository.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public LogRepository defaultLogRepository() {
        return new DefaultLogRepositoryImpl();
    }
}
