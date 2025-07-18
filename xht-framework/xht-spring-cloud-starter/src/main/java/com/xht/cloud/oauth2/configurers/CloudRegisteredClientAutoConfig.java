package com.xht.cloud.oauth2.configurers;

import com.xht.boot.oauth2.configurers.BootOauth2RedisAutoConfig;
import com.xht.boot.oauth2.service.RedisRegisteredClientRepository;
import com.xht.cloud.oauth2.feign.RemoteRegisteredClientService;
import com.xht.cloud.oauth2.feign.factory.RemoteRegisteredClientFallbackFactory;
import com.xht.cloud.oauth2.service.BasicRegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;

/**
 * 自定义spring authorization server配置
 *
 * @author xht
 **/
@Slf4j
@Configuration
@AutoConfigureBefore(BootOauth2RedisAutoConfig.class)
@ConditionalOnClass(OAuth2AuthorizationServerConfigurer.class)
@EnableFeignClients(clients = RemoteRegisteredClientService.class)
public class CloudRegisteredClientAutoConfig {


    public CloudRegisteredClientAutoConfig() {
        log.info("初始化自定义spring authorization server RegisteredClient 配置");
    }

    /**
     * 自定义注册客户端仓库
     *
     * @param redisRegisteredClientRepository redis注册客户端仓库
     * @param remoteRegisteredClientService   远程注册客户端服务
     * @return RegisteredClientRepository
     */
    @Bean
    public RegisteredClientRepository basicRegisteredClientService(RedisRegisteredClientRepository redisRegisteredClientRepository, RemoteRegisteredClientService remoteRegisteredClientService) {
        return new BasicRegisteredClientService(redisRegisteredClientRepository, remoteRegisteredClientService);
    }

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
