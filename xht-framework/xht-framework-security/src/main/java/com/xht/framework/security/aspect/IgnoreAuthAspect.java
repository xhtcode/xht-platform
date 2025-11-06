package com.xht.framework.security.aspect;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.security.exception.IgnoreAuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 内部接口权限切面
 * @author xht
 **/
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IgnoreAuthAspect implements Ordered {

    private final SecurityHeaderProperties securityHeaderProperties;


    /**
     * 内部认证校验
     *
     * @param point 切点
     * @param ignoreAuth 注解
     */
    @SneakyThrows
    @Before(value = "@annotation(com.xht.framework.security.annotation.IgnoreAuth) && @annotation(ignoreAuth)")
    public void ignoreAuthAround(JoinPoint point, IgnoreAuth ignoreAuth) {
        if (ignoreAuth == null) {
            Class<?> clazz = point.getTarget().getClass();
            ignoreAuth = AnnotationUtils.findAnnotation(clazz, IgnoreAuth.class);
        }
        if (Objects.nonNull(ignoreAuth)) {
            HttpServletRequest request = ServletUtil.getHttpServletRequest();
            ThrowUtils.notNull(request, "request cannot be null");
            String authValue = request.getHeader(securityHeaderProperties.getAuthKey());
            // 内部请求验证
            if (ignoreAuth.aop() && !StringUtils.equals(securityHeaderProperties.getAuthValue(), authValue)) {
                log.warn("请求地址'{}'，没有内部访问权限", request.getRequestURI());
                throw new IgnoreAuthException("没有内部访问权限，不允许访问");
            }
        }
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
