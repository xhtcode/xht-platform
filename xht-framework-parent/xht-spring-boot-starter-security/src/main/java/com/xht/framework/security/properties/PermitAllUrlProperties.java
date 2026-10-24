package com.xht.framework.security.properties;

import cn.hutool.core.util.ReUtil;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.utils.spring.SpringContextUtils;
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
@ConfigurationProperties(prefix = "xht.security.whitelist")
public class PermitAllUrlProperties implements InitializingBean {

    private static final String ANSI_GREEN = "\033[32m";

    private static final String ANSI_RESET = "\033[0m";

    /**
     * 正则表达式
     */
    @SuppressWarnings("all")
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");


    /**
     * 默认忽略URL
     */
    private static final String[] DEFAULT_IGNORE_URLS = new String[]{"/favicon.ico", "/actuator/**", "/error", "/webjars/**"};

    /**
     * 默认忽略URL
     */
    private static final String[] DEFAULT_SWAGGER_IGNORE_URLS = new String[]{"/v3/api-docs", "/v3/api-docs/*", "/swagger-ui/*", "/doc.html"};

    /**
     * 常规全部
     */
    private List<String> endpoints = new ArrayList<>();

    /**
     * 是否添加默认忽略URL
     */
    private boolean addDefaultIgnoreUrls = true;

    /**
     * 是否添加swagger默认忽略URL
     */
    private boolean addSwaggerIgnoreUrls = false;

    @Override
    public void afterPropertiesSet() {
        if (addDefaultIgnoreUrls) {
            endpoints.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
        }
        if (addSwaggerIgnoreUrls) {
            endpoints.addAll(Arrays.asList(DEFAULT_SWAGGER_IGNORE_URLS));
        }
        RequestMappingHandlerMapping mapping = SpringContextUtils.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> mappingHandlerMethods = mapping.getHandlerMethods();
        mappingHandlerMethods.keySet().forEach(info -> {
            HandlerMethod handlerMethod = mappingHandlerMethods.get(info);
            // 获取方法上边的注解 替代path variable 为 *
            IgnoreAuth method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), IgnoreAuth.class);
            Optional.ofNullable(method)
                    .ifPresent(ignore -> Objects.requireNonNull(info.getPathPatternsCondition())
                            .getPatternValues()
                            .forEach(url -> endpoints.add(ReUtil.replaceAll(url, PATTERN, "*"))));

            // 获取类上边的注解, 替代path variable 为 *
            IgnoreAuth controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), IgnoreAuth.class);
            Optional.ofNullable(controller)
                    .ifPresent(ignore -> {
                        // 只针对「直接声明」判冲突：方法注解可能继承自契约接口，此时与类级注解并不矛盾
                        boolean methodDirect = handlerMethod.getMethod().isAnnotationPresent(IgnoreAuth.class);
                        boolean typeDirect = handlerMethod.getBeanType().isAnnotationPresent(IgnoreAuth.class);
                        if (methodDirect && typeDirect) {
                            throw new BusinessException(handlerMethod.getBeanType() + "方法上边和类上边不能同时添加@IgnoreAuth注解");
                        }
                        if (Objects.nonNull(method)) {
                            log.warn("{} 同时存在类级与方法级 @IgnoreAuth（方法注解继承自接口），类级语义为「仅白名单」，请确认方法级 aop 取值符合预期",
                                    handlerMethod.getBeanType());
                        }
                        Objects.requireNonNull(info.getPathPatternsCondition())
                                .getPatternValues()
                                .forEach(url -> endpoints.add(ReUtil.replaceAll(url, PATTERN, "*")));

                    });
        });
        log.debug("\n白名单URL:>>>>>>>>>>>>>>>>>>>>>>>>>>> \n{}{}{}", ANSI_GREEN, JsonUtils.toJsonString(endpoints), ANSI_RESET);
    }

}
