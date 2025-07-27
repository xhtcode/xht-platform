package com.xht.framework.web;

import com.xht.framework.web.convert.IEnumsSerializableConverterFactory;
import com.xht.framework.web.handler.DefaultGlobalExceptionHandler;
import com.xht.framework.web.xss.XSSProperties;
import com.xht.framework.web.xss.filter.XSSFilter;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述 ：web组件自动装配
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(XSSProperties.class)
public class WebAutoConfiguration implements WebMvcConfigurer {

    public WebAutoConfiguration() {
        log.debug(">>>>>>web-start web自动装配<<<<<<");
    }

    @Bean
    public DefaultGlobalExceptionHandler congoMallGlobalExceptionHandler() {
        return new DefaultGlobalExceptionHandler();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new IEnumsSerializableConverterFactory());
    }

    /**
     * 创建 XssFilter Bean，解决 Xss 安全问题
     */
    @Bean
    @ConditionalOnBean(PathMatcher.class)
    @ConditionalOnProperty(value = "xht.safety.xss.enable", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<XSSFilter> xssFilter(XSSProperties properties, PathMatcher pathMatcher) {
        FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XSSFilter(properties, pathMatcher));
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registration;
    }
}
