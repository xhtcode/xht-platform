package com.xht.generate;

import com.xht.framework.swagger.annotation.EnableCustomSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代码生成器启动类
 *
 * @author xht
 **/
@Slf4j
@EnableCustomSwagger
@EnableDiscoveryClient
@SpringBootApplication
public class GenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenerateApplication.class, args);
    }

}