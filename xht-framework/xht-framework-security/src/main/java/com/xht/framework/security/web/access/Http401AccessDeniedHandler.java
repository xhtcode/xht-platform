package com.xht.framework.security.web.access;


import com.xht.framework.core.domain.R;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 请求未授权的接口处理器`
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public class Http401AccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.debug("请求未授权的接口处理器 {}: {}", request.getRequestURI(), accessDeniedException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());
        // @formatter:off
        R<Void> build = R
                .error()
                .info(GlobalErrorStatusCode.UNAUTHORIZED)
                .msg(accessDeniedException.getMessage()).build();
        // @formatter:on
        ServletUtil.writeJson(response, build);
    }

}
