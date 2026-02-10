package com.xht.modules.monitor;

import com.xht.framework.oauth2.annotation.EnableResourceServer;
import com.xht.framework.swagger.annotation.EnableCustomSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控服务启动类
 *
 * @author xht
 **/
@Slf4j
@EnableCustomSwagger
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
