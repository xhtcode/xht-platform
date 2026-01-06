package com.xht.framework.log;

import com.xht.framework.log.aspect.BLogAspect;
import com.xht.framework.log.aspect.MethodTimeAspect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 日志自动装配
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class LogAutoConfiguration {

    /**
     * 日志切面
     *
     * @return {@link BLogAspect}
     */
    @Bean
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