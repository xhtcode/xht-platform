package com.xht.framework.openfeign.interceptor;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class FeignAuthenticationInterceptor implements RequestInterceptor, Ordered {

    private final SecurityHeaderProperties securityHeaderProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Method method = requestTemplate.methodMetadata().method();
        NoAuthentication noAuthentication = method.getAnnotation(NoAuthentication.class);
        if (Objects.nonNull(noAuthentication)) {
            requestTemplate.header(securityHeaderProperties.getAuthKey(), securityHeaderProperties.getAuthValue());
        }
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
