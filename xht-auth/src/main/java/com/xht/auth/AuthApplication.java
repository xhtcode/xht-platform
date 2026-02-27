package com.xht.auth;

import com.xht.boot.oauth2.EnableResourceServer;
import com.xht.boot.swagger.EnableCustomSwagger;
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
@EnableCustomSwagger
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableRedisRepositories(enableKeyspaceEvents = ON_STARTUP)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
