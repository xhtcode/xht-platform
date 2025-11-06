package com.xht.auth;

import com.xht.framework.oauth2.annotation.EnableResourceServer;
import com.xht.framework.openfeign.annotation.EnableOpenFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import static org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP;

/**
 * 认证服务启动类
 *
 * @author xht
 **/
@EnableRedisRepositories(enableKeyspaceEvents = ON_STARTUP)
@EnableResourceServer
@EnableOpenFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
