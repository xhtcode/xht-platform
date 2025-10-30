package com.xht.auth.authorization.token;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * token 注销失败处理器
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public class TokenRevocationAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("Authentication failure");
        if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            OAuth2Error error = oAuth2AuthenticationException.getError();
            ServletUtil.write(response, R.errorMsgData(error.getDescription(), error));
        } else {
            log.warn(AuthenticationException.class.getSimpleName() + " must be of type "
                    + OAuth2AuthenticationException.class.getName() + " but was "
                    + exception.getClass().getName());
        }
    }
}
