package com.xht.framework.security.properties;

import cn.hutool.core.util.ReUtil;
import com.xht.framework.core.utils.spring.SpringContextUtil;
import com.xht.framework.security.annotation.InnerAuth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * URL白名单配置
 *
 * @author xht
 **/
@Slf4j
@Data
@ConfigurationProperties(prefix = "xht.security.ignore.whites")
public class PermitAllUrlProperties implements InitializingBean {

    /**
     * 正则表达式
     */
    @SuppressWarnings("all")
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");


    /**
     * 默认忽略URL
     */
    private static final String[] DEFAULT_IGNORE_URLS = new String[]{"/actuator/**",
            "/error", "/v3/api-docs", "/v3/api-docs/*", "/doc.html", "/webjars/**"};

    /**
     * 常规全部
     */
    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        urls.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
        RequestMappingHandlerMapping mapping = SpringContextUtil.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> mappingHandlerMethods = mapping.getHandlerMethods();
        mappingHandlerMethods.keySet().forEach(info -> {
            HandlerMethod handlerMethod = mappingHandlerMethods.get(info);
            // 获取方法上边的注解 替代path variable 为 *
            InnerAuth method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), InnerAuth.class);
            Optional.ofNullable(method)
                    .ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                            .getPatternValues()
                            .forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

            // 获取类上边的注解, 替代path variable 为 *
            InnerAuth controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), InnerAuth.class);
            Optional.ofNullable(controller)
                    .ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                            .getPatternValues()
                            .forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
        });
        log.debug("PermitAllUrlProperties init success, urls: {}", urls);
    }

}
