package com.xht.platform.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * 线程池配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class ThreadPollConfig implements AsyncConfigurer {

    /**
     * 线程池 配置bean名称
     */
    public static final String ASYNC_EXECUTOR_THREAD_NAME = "xht-async-executor";

    /**
     * 配置线程池
     * <p>
     * 该 bean 同时作为全局默认异步执行器（见 {@link #getAsyncExecutor()}）以及 {@code @Async("xht-async-executor")}
     * 显式指定的线程池（缩略图异步生成等场景）。
     *
     * @return 线程池
     */
    @Bean(name = ASYNC_EXECUTOR_THREAD_NAME)
    public AsyncTaskExecutor executor() {
        int processors = Runtime.getRuntime().availableProcessors();
        int threadCount = Math.max(1, processors - 1);
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数量
        taskExecutor.setCorePoolSize(threadCount);
        // 最大线程数量
        taskExecutor.setMaxPoolSize(threadCount);
        taskExecutor.setThreadNamePrefix(ASYNC_EXECUTOR_THREAD_NAME);
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 全局异步执行器：显式绑定 {@link #executor()} 产出的线程池，
     * 避免未指定线程池的 {@code @Async} 退化为每次新建线程的 SimpleAsyncTaskExecutor。
     * <p>
     * 本类为 {@code @AutoConfiguration}（默认 proxyBeanMethods=true），此处对 {@link #executor()} 的调用
     * 会被 CGLIB 代理拦截，返回与 {@code @Bean} 同名的单例，保证 {@code @Async} 与默认执行器使用同一线程池。
     */
    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    /**
     * 自定义异步任务异常处理：仅对「无返回值且未捕获异常」的 {@code @Async} 方法生效。
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /**
     * 自定义异常处理
     */
    @SuppressWarnings("all")
    public static class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            log.error("异步任务发生异常:{}, 参数:{}, ", method.getDeclaringClass().getSimpleName() + "." + method.getName(), Arrays.toString(objects), throwable);
        }
    }
}
