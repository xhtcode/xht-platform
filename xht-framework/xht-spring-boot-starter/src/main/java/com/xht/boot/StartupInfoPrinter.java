package com.xht.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动成功后打印项目信息
 *
 * @author xht
 */
@Component
public class StartupInfoPrinter implements ApplicationRunner {

    /**
     * 项目名称
     */
    @Value("${spring.application.name:未知项目}")
    private String applicationName;

    /**
     * 服务器端口号
     */
    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * 应用上下文路径
     */
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Override
    @SuppressWarnings("all")
    public void run(ApplicationArguments args) throws Exception {
        // 打印启动成功信息
        StringBuilder sb = new StringBuilder();
        sb.append("\n----------------------------------------------------------\n");
        sb.append("\t项目启动成功!\n");
        sb.append("\t项目名称:\t").append(applicationName).append("\n");
        sb.append("\t接口文档:\thttp://127.0.0.1:").append(serverPort).append(contextPath).append("/doc.html\n");
        sb.append("\t接口文档:\thttp://127.0.0.1:").append(serverPort).append(contextPath).append("/swagger-ui/index.html\n");
        sb.append("\t健康检查:\thttp://127.0.0.1:").append(serverPort).append(contextPath).append("/health\n");
        sb.append("\t开发文档:\thttps://xhtcode.github.io/xht-cloud-doc/\n");
        sb.append("----------------------------------------------------------");
        System.out.println(sb.toString());
    }
}
    