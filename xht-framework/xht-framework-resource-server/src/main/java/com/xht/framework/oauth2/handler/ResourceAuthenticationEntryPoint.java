package com.xht.framework.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 资源认证异常处理入口点，用于身份认证失效处理等
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class ResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    @SuppressWarnings("all")
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(HttpConstants.Character.UTF8.getValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        R<String> result = R.error(GlobalErrorStatusCode.UNAUTHORIZED);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (authException != null) {
            result.setMsg("error");
            result.setData(authException.getMessage());
        }
        // 针对令牌过期返回特殊的 424
        if (authException instanceof InvalidBearerTokenException
                || authException instanceof InsufficientAuthenticationException) {
            response.setStatus(GlobalErrorStatusCode.TOKEN_EXPIRED.getCode());
            result.setMsg("请求令牌已过期");
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
