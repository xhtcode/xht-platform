package com.xht.platform.dict.client.factory;

import com.xht.platform.dict.client.SysDictFeignClient;
import com.xht.platform.dict.client.fallback.SysDictClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 字典项查询服务 FallbackFactory
 * <p>
 * 由 {@code DictAutoConfiguration} 以 {@code @Bean} 注册，不再使用 {@code @Component}：
 * 本模块是自动装配模块，不依赖调用方的组件扫描路径。
 *
 * @author xht
 **/
@Slf4j
public class SysDictClientFallbackFactory implements FallbackFactory<SysDictFeignClient> {

    /**
     * 创建 fallback 实例
     *
     * @param cause cause of an exception.
     * @return fallback 实例
     */
    @Override
    public SysDictFeignClient create(Throwable cause) {
        log.error("字典项查询接口调用错误: {}", cause.getMessage(), cause);
        return new SysDictClientFallback(cause);
    }

}
