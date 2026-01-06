package com.xht.boot;

import com.xht.boot.runner.StartupInfoPrinter;
import com.xht.boot.security.SecurityUserContextServiceImpl;
import com.xht.framework.core.support.security.UserContextService;
import com.xht.framework.oauth2.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * xht-boot 自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class XhtBootAutoConfiguration {

    /**
     * 项目启动成功后打印关键信息（包含项目名称、IP地址、接口文档等访问入口）
     *
     * @return 项目启动成功后打印关键信息
     */
    @Bean
    public StartupInfoPrinter startupInfoPrinter() {
        return new StartupInfoPrinter();
    }

    /**
     * 用户上下文服务接口实现
     *
     * @return 用户上下文服务接口实现
     */
    @Bean
    @ConditionalOnClass(SecurityUtils.class)
    public UserContextService securityUserContextServiceImpl() {
        return new SecurityUserContextServiceImpl();
    }

}
