package com.xht.auth.security.web.authentication;

import com.xht.auth.security.web.OAuth2ErrorHelper;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.oauth2.token.response.Oauth2ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Token认证失败处理器
 *
 * @author xht
 **/
@Slf4j
public class TokenEndpointFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("Token认证失败处理器 :{}", exception.getMessage(), exception);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Oauth2ErrorResponse errorResponse = new Oauth2ErrorResponse();
        if (exception instanceof OAuth2AuthenticationException oauth2AuthenticationException) {
            OAuth2Error error = oauth2AuthenticationException.getError();
            String errorCode = error.getErrorCode();
            errorResponse.setError(errorCode);
            errorResponse.setErrorDescription(error.getDescription());
            errorResponse.setErrorUri(error.getUri());
            OAuth2ErrorHelper helper = new OAuth2ErrorHelper(error);
            httpStatus = helper.getHttpStatus();
            errorResponse.setCode(helper.getCode());
            errorResponse.setMessage(helper.getMessage());
        } else {
            errorResponse.setCode(500);
            errorResponse.setMessage(exception.getMessage());
        }
        ServletUtil.writeJson(response, httpStatus, errorResponse);
    }
}
