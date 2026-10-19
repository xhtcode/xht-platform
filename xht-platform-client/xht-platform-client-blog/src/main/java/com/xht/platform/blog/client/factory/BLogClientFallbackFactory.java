package com.xht.platform.blog.client.factory;

import com.xht.platform.blog.client.BLogFeignClient;
import com.xht.platform.blog.client.fallback.BLogClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 日志保存 FallbackFactory
 * <p>
 * 由 {@code BLogAutoConfiguration} 以 {@code @Bean} 注册，不再使用 {@code @Component}：
 * 本模块是自动装配模块，不依赖调用方的组件扫描路径。
 *
 * @author xht
 **/
@Slf4j
public class BLogClientFallbackFactory implements FallbackFactory<BLogFeignClient> {

    @Override
    public BLogFeignClient create(Throwable cause) {
        log.error("日志保存接口调用错误: {}", cause.getMessage(), cause);
        return new BLogClientFallback(cause);
    }

}
