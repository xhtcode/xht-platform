package com.xht.system;

import com.xht.framework.oauth2.annotation.EnableResourceServer;
import com.xht.framework.swagger.annotation.EnableCustomSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统模块启动类
 *
 * @author xht
 **/
@Slf4j
@EnableCustomSwagger
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
