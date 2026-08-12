package com.xht.platform.dict;

import com.xht.platform.common.dict.api.ISysDictClient;
import com.xht.platform.common.dict.factory.ISysDictFactory;
import com.xht.platform.dict.factory.SysDictApiFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@ComponentScan(value = "com.xht.platform.common.dict.api.factory")
@EnableFeignClients(clients = ISysDictClient.class)
public class DictAutoConfiguration {

    public DictAutoConfiguration() {
        log.debug("系统字典自动配置启动");
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
