package com.xht.boot.runner;

import cn.hutool.core.date.DateUtil;
import com.xht.framework.core.properties.XhtConfigProperties;
import com.xht.framework.core.properties.basic.EnableProperties;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Optional;

/**
 * 项目启动成功后打印关键信息（包含项目名称、IP地址、接口文档等访问入口）
 * 实现ApplicationRunner接口，确保在Spring容器完全初始化后执行
 *
 * @author xht
 */
@Slf4j
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
     * ASCII艺术字（Welcome主题，调整缩进确保显示整齐）
     */
    private static final String ASCII_ART = """
             __        __         _                                     _\s
             \\ \\      / /   ___  | |   ___    ___    _ __ ___     ___  | |
              \\ \\ /\\ / /   / _ \\ | |  / __|  / _ \\  | '_ ` _ \\   / _ \\ | |
               \\ V  V /   |  __/ | | | (__  | (_) | | | | | | | |  __/ |_|
                \\_/\\_/     \\___| |_|  \\___|  \\___/  |_| |_| |_|  \\___| (_)
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

    @Resource
    private XhtConfigProperties xhtConfigProperties;

    @Override
    @SuppressWarnings("all")
    public void run(ApplicationArguments args) {
        // @formatter:off
        Boolean banner = Optional.ofNullable(xhtConfigProperties)
                .map(XhtConfigProperties::getGlobal)
                .map(XhtConfigProperties.GlobalConfigProperties::getBanner)
                .map(EnableProperties::isEnable)
                .orElse(true);
        // @formatter:on
        if (banner) {
            // 获取并校验本机IP地址（防止获取失败导致的空指针）
            String serverIp = StringUtils.emptyToDefault(IpUtils.getHostIp(), DEFAULT_IP);
            StringBuilder sb = new StringBuilder();
            appendString(sb, SEPARATOR);
            appendString(sb, "\n");
            appendString(sb, ASCII_ART);
            appendString(sb, "\n");
            appendString(sb, "🙋‍♂️项目作者:\t👉小糊涂(xht)👈%n");
            appendString(sb, "⌚启动时间:\t👉%s👈%n", DateUtil.now());
            appendString(sb, "🌴项目名称:\t%s%n", applicationName);
            appendString(sb, "🌎当前环境:\t%s%n", activeProfile);
            appendString(sb, "🌐本地访问地址:\thttp://localhost:%s%s%n", serverPort, contextPath);
            appendString(sb, "🌐外部访问地址:\thttp://%s:%s%s%n", serverIp, serverPort, contextPath);
            appendString(sb, "📚 接口文档地址:%n");
            appendString(sb, "   ├─ Knife4j文档:\thttp://localhost:%s%s/doc.html%n", serverPort, contextPath);
            appendString(sb, "   ├─ Swagger文档:\thttp://localhost:%s%s/swagger-ui.html%n", serverPort, contextPath);
            appendString(sb, "   └─ OpenAPI规范:\thttp://localhost:%s%s/v3/api-docs%n", serverPort, contextPath);
            appendString(sb, "\n");
            appendString(sb, SEPARATOR);
            log.info("\n{}", sb);
        }
    }

    private static void appendString(StringBuilder sb, String format, Object... args) {
        sb.append(String.format(format, args));
    }

}
