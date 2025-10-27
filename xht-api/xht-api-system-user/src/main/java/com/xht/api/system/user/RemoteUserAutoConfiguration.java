package com.xht.api.system.user;

import com.xht.api.system.user.feign.RemoteUserService;
import com.xht.api.system.user.feign.factory.RemoteUserServiceFallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 系统用户 API自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@EnableFeignClients(clients = RemoteUserService.class)
public class RemoteUserAutoConfiguration {


    /**
     * 用户服务降级工厂
     *
     * @return 用户服务降级工厂
     */
    @Bean
    public RemoteUserServiceFallbackFactory userServiceFallbackFactory() {
        return new RemoteUserServiceFallbackFactory();
    }
}
