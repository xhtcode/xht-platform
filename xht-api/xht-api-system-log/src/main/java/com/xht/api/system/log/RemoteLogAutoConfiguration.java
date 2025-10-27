package com.xht.api.system.log;

import com.xht.api.system.log.feign.RemoteLogClientService;
import com.xht.api.system.log.feign.factory.RemoteLogClientFallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 系统日志API自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@EnableFeignClients(clients = RemoteLogClientService.class)
public class RemoteLogAutoConfiguration {

    /**
     * RemoteLogClientFallbackFactory 实现类
     *
     * @return RemoteLogClientFallbackFactory 实现类
     */
    @Bean
    public RemoteLogClientFallbackFactory remoteLogClientFallbackFactory() {
        return new RemoteLogClientFallbackFactory();
    }

}
