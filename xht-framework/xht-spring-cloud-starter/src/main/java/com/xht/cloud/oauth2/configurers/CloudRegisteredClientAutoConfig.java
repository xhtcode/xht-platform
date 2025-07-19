package com.xht.cloud.oauth2.configurers;

import com.xht.cloud.oauth2.feign.RemoteRegisteredClientService;
import com.xht.cloud.oauth2.feign.factory.RemoteRegisteredClientFallbackFactory;
import com.xht.cloud.oauth2.service.BasicRegisteredClientRepository;
import lombok.extern.slf4j.Slf4j;
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
@ConditionalOnClass(OAuth2AuthorizationServerConfigurer.class)
@EnableFeignClients(clients = RemoteRegisteredClientService.class)
public class CloudRegisteredClientAutoConfig {


    public CloudRegisteredClientAutoConfig() {
        log.info("初始化自定义spring authorization server RegisteredClient 配置");
    }

    /**
     * 自定义注册客户端仓库
     *
     * @return RegisteredClientRepository
     */
    @Bean
    public RegisteredClientRepository basicRegisteredClientService() {
        return new BasicRegisteredClientRepository();
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
