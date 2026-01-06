package com.xht.framework.mybatis.blog;

import com.xht.framework.mybatis.blog.listener.BLogApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
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
@ConditionalOnProperty(value = "xht.blog.repository-type", havingValue = "jdbc")
public class BlogAutoConfiguration {

    /**
     * jdbc 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    public BLogApplicationListener jdbcBlogApplicationListener() {
        return new BLogApplicationListener();
    }

    /**
     * jdbc 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    public MapperScannerConfigurer bLogMapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.xht.framework.mybatis.blog.mapper");
        return scannerConfigurer;
    }

}
