package com.xht.boot.log;

import com.xht.framework.log.aspect.BLogAspect;
import com.xht.framework.log.aspect.MethodTimeAspect;
import com.xht.framework.log.repository.BLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * 日志自动装配
 *
 * @author xht
 **/
@Slf4j
@ConditionalOnClass(BLogRepository.class)
@AutoConfiguration
@RequiredArgsConstructor
public class LogAutoConfiguration {

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