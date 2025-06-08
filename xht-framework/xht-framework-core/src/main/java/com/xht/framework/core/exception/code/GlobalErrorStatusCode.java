package com.xht.framework.core.exception.code;


import com.xht.framework.core.constant.basic.Constants;
import lombok.Getter;

/**
 * 描述 ：全局 状态码
 *
 * @author xht
 **/
@Getter
public enum GlobalErrorStatusCode implements ErrorCode {

    SUCCESS(Constants.SUCCESS, "成功"),

    BAD_REQUEST(400, "错误的请求"),

    UNAUTHORIZED(401, "认证失败"),

    FORBIDDEN(403, "权限不足"),

    NOT_FOUND(404, "您要找的资源不见啦，有可能被外星人抓走了..."),

    PARAM_INVALID(415, "无效的请求参数"),

    ERROR(Constants.FAIL, "服务器又在偷懒了，请稍后重试"),
    ;


    private final int code;

    private final String msg;


    GlobalErrorStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}
