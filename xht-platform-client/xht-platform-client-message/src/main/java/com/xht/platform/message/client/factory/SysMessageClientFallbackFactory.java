package com.xht.platform.message.client.factory;

import com.xht.platform.message.client.SysMessageFeignClient;
import com.xht.platform.message.client.fallback.SysMessageClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 系统管理-站内信 FallbackFactory
 * <p>
 * 由 {@code MessageAutoConfiguration} 以 {@code @Bean} 注册，不再使用 {@code @Component}：
 * 本模块是自动装配模块，不依赖调用方的组件扫描路径。
 *
 * @author xht
 **/
@Slf4j
public class SysMessageClientFallbackFactory implements FallbackFactory<SysMessageFeignClient> {

    @Override
    public SysMessageFeignClient create(Throwable cause) {
        log.error("系统管理-站内信接口调用错误: {}", cause.getMessage(), cause);
        return new SysMessageClientFallback(cause);
    }

}
