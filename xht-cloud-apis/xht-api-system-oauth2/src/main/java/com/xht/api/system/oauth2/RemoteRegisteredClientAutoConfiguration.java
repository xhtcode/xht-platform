package com.xht.api.system.oauth2;

import com.xht.api.system.oauth2.feign.RemoteRegisteredClientService;
import com.xht.api.system.oauth2.feign.factory.RemoteRegisteredClientFallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 系统oauth2 API自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@EnableFeignClients(clients = RemoteRegisteredClientService.class)
public class RemoteRegisteredClientAutoConfiguration {

    /**
     * 自定义feign客户端的fallback工厂
     *
     * @return RemoteRegisteredClientFallbackFactory
     */
    @Bean
    public RemoteRegisteredClientFallbackFactory remoteRegisteredClientFallbackFactory() {
        return new RemoteRegisteredClientFallbackFactory();
    }
}
