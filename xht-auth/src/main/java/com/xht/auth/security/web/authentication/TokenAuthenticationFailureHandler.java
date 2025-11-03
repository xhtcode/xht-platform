package com.xht.auth.security.web.authentication;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * Token认证失败处理器
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("Authentication failure:{}", exception.getMessage(), exception);
        ServletUtil.write(response, R.errorMsg(exception.getMessage()));
    }

}
