package com.xht.framework.oauth2.handler;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.CharacterEnums;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 资源认证异常处理入口点，用于身份认证失效处理等
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class ResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    @SuppressWarnings("all")
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(CharacterEnums.UTF_8.getValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        R.RBuilder result = R.error().info(GlobalErrorStatusCode.UNAUTHORIZED);
        if (StringUtils.hasText(authException.getMessage())) {
            result.msg(authException.getMessage());
        }
        // 针对令牌过期返回特殊的 424
        if (authException instanceof InvalidBearerTokenException
                || authException instanceof InsufficientAuthenticationException) {
            result.info(GlobalErrorStatusCode.TOKEN_EXPIRED);
        }
        log.error("认证失败: {}", authException.getMessage(), authException);
        ServletUtil.writeJson(response, result.build());
    }
}
