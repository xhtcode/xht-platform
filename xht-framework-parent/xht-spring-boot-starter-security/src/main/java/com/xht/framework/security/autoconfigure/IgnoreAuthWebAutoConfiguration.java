package com.xht.framework.security.autoconfigure;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.security.interceptor.IgnoreAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 内部接口权限拦截器自动装配
 *
 * <p>仅在 Servlet MVC 环境下装配；{@code spring-webmvc} 在本 starter 中是 optional 依赖，
 * 必须用 {@link ConditionalOnClass} 兜底，避免非 MVC 服务（如 WebFlux 网关）加载失败。</p>
 *
 * <p>注意：本类只允许依赖 {@link SecurityHeaderProperties}。切勿注入 {@code PermitAllUrlProperties}，
 * 后者在 {@code afterPropertiesSet()} 中主动触发 {@code requestMappingHandlerMapping} 的创建，
 * 而该 Bean 的创建又会实例化所有 {@link WebMvcConfigurer}，形成
 * {@code BeanCurrentlyInCreationException}。</p>
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(WebMvcConfigurer.class)
public class IgnoreAuthWebAutoConfiguration implements WebMvcConfigurer {

    private final SecurityHeaderProperties securityHeaderProperties;

    public IgnoreAuthWebAutoConfiguration(SecurityHeaderProperties securityHeaderProperties) {
        this.securityHeaderProperties = securityHeaderProperties;
        log.debug("【xht-security-spring-boot-starter】 IgnoreAuthWebAutoConfiguration 启动成功！");
    }

    /**
     * 注册内部接口权限拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new IgnoreAuthInterceptor(securityHeaderProperties))
                // 内部头校验越早越好，同时留出位置给基础设施拦截器
                .order(Ordered.HIGHEST_PRECEDENCE + 10)
                .addPathPatterns("/**");
    }

}
