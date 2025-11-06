package com.xht.framework.openfeign.interceptor;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Feign 忽略权限认证拦截器
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class FeignIgnoreAuthInterceptor implements RequestInterceptor, Ordered {

    private final SecurityHeaderProperties securityHeaderProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Method method = requestTemplate.methodMetadata().method();
        FeignIgnoreAuth feignIgnoreAuth = method.getAnnotation(FeignIgnoreAuth.class);
        if (Objects.nonNull(feignIgnoreAuth)) {
            requestTemplate.header(securityHeaderProperties.getAuthKey(), securityHeaderProperties.getAuthValue());
        }
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
