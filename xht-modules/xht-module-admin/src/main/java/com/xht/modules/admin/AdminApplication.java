package com.xht.modules.admin;

import com.xht.boot.oauth2.EnableResourceServer;
import com.xht.boot.swagger.EnableCustomSwagger;
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
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
