package com.xht.boot.init;

import cn.hutool.core.date.DateUtil;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动成功后打印关键信息（包含项目名称、IP地址、接口文档等访问入口）
 * 实现ApplicationRunner接口，确保在Spring容器完全初始化后执行
 *
 * @author 小糊涂
 */
@Component
@SuppressWarnings("all")
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
     * 启动成功提示语（常量定义，便于统一修改）
     */
    private static final String STARTUP_TIP = "项目启动成功, 小糊涂学编程越学越糊涂!";

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

    @Override
    public void run(ApplicationArguments args) {
        // 获取并校验本机IP地址（防止获取失败导致的空指针）
        String serverIp = IpUtils.getHostIp();
        if (!StringUtils.hasText(serverIp)) {
            serverIp = DEFAULT_IP;
        }

        // 构建输出信息（使用StringBuilder提升多字符串拼接性能）
        StringBuilder output = new StringBuilder();
        output.append(SEPARATOR).append("\n")
                .append(ASCII_ART).append("\n")
                .append(SEPARATOR).append("\n")
                .append("\t").append(STARTUP_TIP).append("\n")
                .append("\t项目名称:\t").append(applicationName).append("\n")
                .append("\t启动时间:\t").append(DateUtil.now()).append("\n")
                // 接口文档地址（127.0.0.1）
                .append("\t接口文档:\t").append(buildUrl("127.0.0.1", "/doc.html")).append("\n")
                // 接口文档地址（本机IP）
                .append("\t接口文档:\t").append(buildUrl(serverIp, "/doc.html")).append("\n")
                // Swagger地址（127.0.0.1）
                .append("\t接口文档:\t").append(buildUrl("127.0.0.1", "/swagger-ui/index.html")).append("\n")
                // Swagger地址（本机IP）
                .append("\t接口文档:\t").append(buildUrl(serverIp, "/swagger-ui/index.html")).append("\n")
                .append(SEPARATOR);
        new  Thread(()->{
            try {
                Thread.sleep(500L);
                System.out.println(output);
            } catch (InterruptedException e) {
            }
        }).start();
    }

    /**
     * 构建完整的访问URL
     * 封装URL拼接逻辑，减少重复代码，提高可维护性
     *
     * @param ip   访问IP（如127.0.0.1或本机IP）
     * @param path 接口路径（如/doc.html）
     * @return 完整URL
     */
    private String buildUrl(String ip, String path) {
        // 处理上下文路径可能带有的斜杠重复问题
        String cleanContextPath = StringUtils.hasText(contextPath) ? contextPath.trim() : "";
        String cleanPath = path.startsWith("/") ? path : "/" + path;

        // 拼接上下文路径和接口路径（处理斜杠重复）
        String fullPath = cleanContextPath + cleanPath;
        fullPath = fullPath.replace("//", "/");

        // 构建完整URL
        return String.format("http://%s:%s%s", ip, serverPort, fullPath);
    }
}
