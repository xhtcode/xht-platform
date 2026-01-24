package com.xht.modules.admin.config;

import com.xht.modules.admin.audit.service.IBLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 日志自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class BlogAutoConfiguration {

    /**
     * jdbc 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    @ConditionalOnProperty(value = "xht.blog.repository-type", havingValue = "jdbc")
    public BLogApplicationListener jdbcBlogApplicationListener(IBLogService ibLogService) {
        return new BLogApplicationListener(ibLogService);
    }

}
