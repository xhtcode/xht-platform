package com.xht.framework.security.handler;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * spring security全局异常处理
 *
 * @author xht
 **/
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    /**
     * controller 接口拦截  {@link AuthenticationException}
     */
    @ExceptionHandler(AuthenticationException.class)
    public R<String> handle(AuthenticationException e, HttpServletRequest request) {
        log.debug(" {} 认证失败: {}", request.getRequestURI(), e.getMessage(), e);
        return R.error().info(GlobalErrorStatusCode.UNAUTHORIZED).build();
    }

    /**
     * controller 接口拦截  {@link AccessDeniedException}
     */
    @ExceptionHandler(AccessDeniedException.class)
    public R<String> handle(AccessDeniedException e, HttpServletRequest request) {
        log.debug(" {} 权限不足: {}", request.getRequestURI(), e.getMessage(), e);
        return R.error().info(GlobalErrorStatusCode.FORBIDDEN).build();
    }

}
