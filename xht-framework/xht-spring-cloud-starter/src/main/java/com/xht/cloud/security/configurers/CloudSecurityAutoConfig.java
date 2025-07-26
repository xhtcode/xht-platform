package com.xht.cloud.security.configurers;

import com.xht.cloud.security.dao.RemoteUserDetailsDao;
import com.xht.cloud.security.feign.RemoteUserService;
import com.xht.cloud.security.feign.factory.RemoteUserServiceFallbackFactory;
import com.xht.framework.security.configurers.SecurityConfigurer;
import com.xht.framework.security.core.userdetails.UserDetailsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring security  cloud starter 自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration
@ConditionalOnClass(SecurityConfigurer.class)
@EnableFeignClients(clients = RemoteUserService.class)
public class CloudSecurityAutoConfig {

    public CloudSecurityAutoConfig() {
        log.info("初始化 spring security  cloud starter 自动配置...");
    }

    /**
     * 用户服务降级工厂
     *
     * @return 用户服务降级工厂
     */
    @Bean
    public RemoteUserServiceFallbackFactory userServiceFallbackFactory() {
        return new RemoteUserServiceFallbackFactory();
    }

    /**
     * 用户服务
     *
     * @param remoteUserService 远程用户服务
     * @return 用户服务
     */
    @Bean
    public UserDetailsDao remoteUserDetailsDao(RemoteUserService remoteUserService) {
        return new RemoteUserDetailsDao(remoteUserService);
    }

}
