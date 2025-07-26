package com.xht.framework.security.aspect;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.core.utils.HttpServletUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.aspect.AopUtils;
import com.xht.framework.security.annotation.InnerAuth;
import com.xht.framework.security.exception.InnerAuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xht
 **/
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class InnerAuthAspect implements Ordered {

    private final SecurityHeaderProperties securityHeaderProperties;

    /**
     *
     */
    @Pointcut(value = "@within(com.xht.framework.security.annotation.InnerAuth) || @annotation(com.xht.framework.security.annotation.InnerAuth)")
    public void innerAuthCut() {
    }

    /**
     * 内部认证校验
     *
     * @param point 切点
     * @return object
     */
    @SneakyThrows
    @Around(value = "innerAuthCut()")
    public Object innerAuthAround(ProceedingJoinPoint point) {
        InnerAuth innerAuth = AopUtils.getAnnotation(point, InnerAuth.class);
        if (Objects.isNull(innerAuth)) {
            return point.proceed();
        }
        HttpServletRequest request = HttpServletUtils.getHttpServletRequest();
        ThrowUtils.notNull(request, "request cannot be null");
        String source = request.getHeader(securityHeaderProperties.getAuthKey());
        // 内部请求验证
        if (!innerAuth.isAnonymous() && !StringUtils.equals(securityHeaderProperties.getAuthValue(), source)) {
            log.warn("请求地址'{}'，没有内部访问权限", request.getRequestURI());
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }
        return point.proceed();
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
