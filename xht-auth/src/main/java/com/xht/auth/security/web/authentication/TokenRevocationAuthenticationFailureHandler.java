package com.xht.auth.security.web.authentication;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * token 注销失败处理器
 *
 * @author xht
 **/
@Slf4j
public class TokenRevocationAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String message = exception.getMessage();
        log.error("token 注销失败处理器:{}", message, exception);
        ServletUtil.writeJson(response, R.error().msg(message).build());
    }
}
