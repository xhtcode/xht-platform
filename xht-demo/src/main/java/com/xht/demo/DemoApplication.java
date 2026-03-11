package com.xht.demo;

import com.xht.boot.oauth2.EnableResourceServer;
import com.xht.boot.swagger.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author xht
 **/
@EnableCustomSwagger
@EnableResourceServer
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}