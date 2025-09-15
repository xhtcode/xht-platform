package com.xht.boot.runner;

import cn.hutool.core.date.DateUtil;
import com.xht.framework.core.properties.GlobalConfigProperties;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动成功后打印关键信息（包含项目名称、IP地址、接口文档等访问入口）
 * 实现ApplicationRunner接口，确保在Spring容器完全初始化后执行
 *
 * @author xht
 */
@Component
@RequiredArgsConstructor
public class StartupInfoPrinter implements ApplicationRunner {

    /**
     * 分隔线常量，统一输出格式（前后保持一致）
     */
    private static final String SEPARATOR = "------------------------------------------------------------------";

    /**
     * 默认IP地址（当获取失败时使用）
     */
    private static final String DEFAULT_IP = "未知IP";

    /**
     * ASCII艺术字（Welcome主题，调整缩进确保显示整齐）
     */
    private static final String ASCII_ART = """
               __        __   _
               \\ \\      / /__| | ___ ___  _ __ ___   ___
                \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\
                 \\ V  V /  __/ | (_| (_) | | | | | |  __/
                  \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|
            """;

    /**
     * 项目名称（从配置文件获取，默认"未知项目"）
     */
    @Value("${spring.application.name:未知项目}")
    private String applicationName;

    /**
     * 服务器端口（从配置文件获取，默认8080）
     */
    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * 应用上下文路径（从配置文件获取，默认空字符串）
     */
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    private final GlobalConfigProperties globalConfigProperties;

    @Override
    @SuppressWarnings("all")
    public void run(ApplicationArguments args) {
        if (globalConfigProperties.isBanner()) {
            // 获取并校验本机IP地址（防止获取失败导致的空指针）
            String serverIp = IpUtils.getHostIp();
            if (!StringUtils.hasText(serverIp)) {
                serverIp = DEFAULT_IP;
            }
            System.out.println(SEPARATOR);
            System.out.println(ASCII_ART);
            System.out.println("🤡 项目作者：👉小糊涂(xht)👈");
            System.out.printf("⌛ 启动时间：👉%s👈", DateUtil.now());
            System.out.println(SEPARATOR);
            System.out.println();
            System.out.printf("🌴 项目名称:     %s%n", applicationName);
            System.out.printf("🌎 当前环境:     %s%n", activeProfile);
            System.out.printf("🌐 本地访问地址: http://localhost:%s%s%n", serverPort, contextPath);
            System.out.printf("🌐 外部访问地址: http://%s:%s%s%n", serverIp, serverPort, contextPath);
            System.out.println("📚 接口文档地址:");
            System.out.printf("   ├─ Knife4j文档:  http://localhost:%s%s/doc.html%n", serverPort, contextPath);
            System.out.printf("   ├─ Swagger文档:  http://localhost:%s%s/swagger-ui.html%n", serverPort, contextPath);
            System.out.printf("   └─ OpenAPI规范:  http://localhost:%s%s/v3/api-docs%n", serverPort, contextPath);
            System.out.println();
            System.out.println(SEPARATOR);
        }
    }

}
