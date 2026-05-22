package com.xht.auth.security.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

/**
 *
 *
 * @author xht
 **/
@Getter
public final class OAuth2ErrorHelper {
    private int code;

    private HttpStatus httpStatus;

    private String message;

    public OAuth2ErrorHelper(OAuth2Error error) {
        convertError(error);
    }

    private void convertError(OAuth2Error error) {
        String errorCode = error.getErrorCode();
        // 默认值
        this.code = 500;
        this.message = errorCode;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (errorCode) {
            case OAuth2ErrorCodes.INVALID_REQUEST:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "请求参数缺失或格式错误";
                break;

            case OAuth2ErrorCodes.UNAUTHORIZED_CLIENT:
                this.code = 401;
                this.httpStatus = HttpStatus.UNAUTHORIZED;
                this.message = "客户端未授权使用该授权方式";
                break;

            case OAuth2ErrorCodes.ACCESS_DENIED:
                this.code = 403;
                this.httpStatus = HttpStatus.FORBIDDEN;
                this.message = "用户拒绝授权";
                break;

            case OAuth2ErrorCodes.UNSUPPORTED_RESPONSE_TYPE:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "不支持的响应类型";
                break;

            case OAuth2ErrorCodes.INVALID_SCOPE:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "申请的权限范围无效或超出限制";
                break;

            case OAuth2ErrorCodes.INSUFFICIENT_SCOPE:
                this.code = 403;
                this.httpStatus = HttpStatus.FORBIDDEN;
                this.message = "权限不足，无法访问该资源";
                break;

            case OAuth2ErrorCodes.INVALID_TOKEN:
                this.code = 401;
                this.httpStatus = HttpStatus.UNAUTHORIZED;
                this.message = "令牌无效或已过期，请重新登录";
                break;

            case OAuth2ErrorCodes.SERVER_ERROR:
                this.code = 500;
                this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                this.message = "授权服务异常，请稍后重试";
                break;

            case OAuth2ErrorCodes.TEMPORARILY_UNAVAILABLE:
                this.code = 503;
                this.httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
                this.message = "授权服务繁忙，请稍后重试";
                break;

            case OAuth2ErrorCodes.INVALID_CLIENT:
                this.code = 401;
                this.httpStatus = HttpStatus.UNAUTHORIZED;
                this.message = "客户端认证失败（应用ID或密钥错误）";
                break;

            case OAuth2ErrorCodes.INVALID_GRANT:
                this.code = 401;
                this.httpStatus = HttpStatus.UNAUTHORIZED;
                this.message = "用户名或密码错误";
                break;

            case OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "不支持的授权类型";
                break;

            case OAuth2ErrorCodes.UNSUPPORTED_TOKEN_TYPE:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "不支持的令牌类型";
                break;

            case OAuth2ErrorCodes.INVALID_REDIRECT_URI:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "重定向地址无效";
                break;

            case OAuth2ErrorCodes.INVALID_DPOP_PROOF:
                this.code = 400;
                this.httpStatus = HttpStatus.BAD_REQUEST;
                this.message = "DPoP验证失败";
                break;
            default:
                // 使用默认异常提示
                break;
        }
    }
}
