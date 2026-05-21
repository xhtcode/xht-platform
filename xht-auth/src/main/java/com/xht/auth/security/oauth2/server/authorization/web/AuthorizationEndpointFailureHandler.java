package com.xht.auth.security.oauth2.server.authorization.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Objects;

/**
 * oauth2 授权失败 处理器
 *
 * @author xht
 **/
@Slf4j
public class AuthorizationEndpointFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("  oauth2 授权失败 ", exception);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            // 登录已失效
            ServletUtil.writeJson(response, R.error().info(GlobalErrorStatusCode.TOKEN_EXPIRED).build());
            return;
        }
        SecurityContextHolder.clearContext();
        OAuth2AuthorizationCodeRequestAuthenticationException authenticationException = (OAuth2AuthorizationCodeRequestAuthenticationException) exception;
        OAuth2Error error = authenticationException.getError();
        OAuth2AuthorizationCodeRequestAuthenticationToken authenticationToken = authenticationException.getAuthorizationCodeRequestAuthentication();
        if (authenticationToken == null || !StringUtils.hasText(authenticationToken.getRedirectUri())) {
            String errorMsg = StringUtils.emptyToDefault(error.getDescription(), "Invalid or missing redirect_uri");
            log.error("  oauth2 授权失败 {} ", errorMsg, authenticationException);
            //  // 第二次点击“拒绝”会因为之前取消时删除授权申请记录而找不到对应的数据，导致抛出 [invalid_request] OAuth 2.0 Parameter: state
            ServletUtil.writeJson(response, R.error().msg("查询不到对应的授权申请记录").build());
            return;
        }
        AuthorizationEndpointFailureResponse failureResponse = new AuthorizationEndpointFailureResponse();
        String redirectUri = authenticationToken.getRedirectUri();
        String errorCode = error.getErrorCode();
        String resultMsg = "未知错误，授权失败";
        if (Objects.equals(errorCode, OAuth2ErrorCodes.ACCESS_DENIED)) {
            resultMsg = "您未选择scope或拒绝了本次授权申请";
        }
        failureResponse.setError(errorCode);
        failureResponse.setErrorDescription(error.getDescription());
        failureResponse.setErrorUri(error.getUri());
        failureResponse.setState(authenticationToken.getState());
        failureResponse.setRedirectUri(redirectUri);
        ServletUtil.writeJson(response, R.error().msg(resultMsg).build(failureResponse));
    }


    @Data
    public static class AuthorizationEndpointFailureResponse extends BasicResponse {

        /**
         * 错误信息
         */
        @JsonProperty(OAuth2ParameterNames.ERROR)
        private String error;

        /**
         * 错误描述
         */
        @JsonProperty(OAuth2ParameterNames.ERROR_DESCRIPTION)
        private String errorDescription;

        /**
         * 错误uri
         */
        @JsonProperty(OAuth2ParameterNames.ERROR_URI)
        private String errorUri;

        /**
         * 状态
         */
        @JsonProperty(OAuth2ParameterNames.STATE)
        private String state;

        /**
         * 重定向地址
         */
        @JsonProperty(OAuth2ParameterNames.REDIRECT_URI)
        private String redirectUri;

    }
}
