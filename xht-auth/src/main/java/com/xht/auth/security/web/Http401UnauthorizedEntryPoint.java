package com.xht.auth.security.web;

import com.xht.framework.core.domain.R;
import com.xht.framework.web.utils.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 自定义认证EntryPoint 请求未认证的接口时，会调用此类中的commence方法，返回错误信息
 *
 * @author xht
 **/
@Slf4j
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpServletUtils.writeString(response, HttpStatus.UNAUTHORIZED, R.errorMsgData("请求未认证",authException.getMessage()));
    }
}
