package com.xht.platform.dict;

import com.xht.platform.common.dict.factory.ISysDictFactory;
import com.xht.platform.dict.client.SysDictFeignClient;
import com.xht.platform.dict.client.factory.SysDictClientFallbackFactory;
import com.xht.platform.dict.factory.SysDictApiFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = SysDictFeignClient.class)
public class DictAutoConfiguration {

    public DictAutoConfiguration() {
        log.debug("系统字典自动配置启动");
    }

    /**
     * 字典项查询降级工厂
     * <p>
     * 显式注册而非 {@code @Component}，避免把降级的装配责任推给调用方的组件扫描。
     *
     * @return 降级工厂
     */
    @Bean
    public SysDictClientFallbackFactory sysDictClientFallbackFactory() {
        return new SysDictClientFallbackFactory();
    }

    /**
     * 创建字典项查询服务工厂
     *
     * @return 字典项查询服务工厂
     */
    @Bean
    public ISysDictFactory sysDictClient() {
        return new SysDictApiFactory();
    }

}
