package com.xht.workflow;

import com.xht.framework.swagger.autoconfigure.EnableCustomSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 描述： 工作流应用启动类
 *
 * @author xht
 **/
@EnableDiscoveryClient
@EnableCustomSwagger
@SpringBootApplication
public class WorkFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkFlowApplication.class, args);
    }
}
