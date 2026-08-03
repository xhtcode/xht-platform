package com.xht.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Flowable异步任务配置类
 * 配置异步任务执行器，用于处理异步操作
 */
@EnableAsync
@Configuration
public class FlowableAsyncConfig {

    /**
     * 配置异步任务执行器
     *
     * @return 线程池任务执行器
     */
    @Bean(name = "applicationTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("ApplicationTaskExecutor-");
        executor.initialize();
        return executor;
    }

}