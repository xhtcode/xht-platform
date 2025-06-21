package com.xht.auth.authorization.token;

import com.xht.auth.domain.response.OAuth2ErrorResponse;
import com.xht.framework.core.domain.R;
import com.xht.framework.web.utils.HttpServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

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
        log.info("TokenAuthenticationFailureHandler onAuthenticationFailure", exception);
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
            OAuth2ErrorResponse errorResponse = convert(error);
            HttpServletUtils.writeString(response, R.errorMsgData(errorResponse.getErrorDescription(), errorResponse));
        } else {
            log.warn(AuthenticationException.class.getSimpleName() + " must be of type "
                    + OAuth2AuthenticationException.class.getName() + " but was "
                    + exception.getClass().getName());
        }
    }

    public OAuth2ErrorResponse convert(OAuth2Error oauth2Error) {
        OAuth2ErrorResponse parameters = new OAuth2ErrorResponse();
        parameters.setError(oauth2Error.getErrorCode());
        if (StringUtils.hasText(oauth2Error.getDescription())) {
            parameters.setErrorDescription(oauth2Error.getDescription());
        } else {
            parameters.setErrorDescription("unknown error");
        }
        if (StringUtils.hasText(oauth2Error.getUri())) {
            parameters.setErrorUri(oauth2Error.getUri());
        }
        return parameters;
    }
}
