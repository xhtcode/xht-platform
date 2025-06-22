package com.xht.auth.security.web.access;

import com.xht.framework.web.utils.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 请求未授权的接口处理器`
 *
 * @author xht
 **/
@Slf4j
public class Http401AccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());
        HttpServletUtils.writeString(response, HttpStatus.UNAUTHORIZED, accessDeniedException.getMessage());
    }
}
