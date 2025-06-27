package com.xht.framework.core.exception.code;


import com.xht.framework.core.constant.basic.RConstants;
import lombok.Getter;

/**
 * 描述 ：全局 状态码
 *
 * @author xht
 **/
@Getter
public enum GlobalErrorStatusCode implements ErrorCode {

    SUCCESS(RConstants.SUCCESS, "成功"),

    BAD_REQUEST(400, "错误的请求"),

    UNAUTHORIZED(401, "未授权的访问"),

    FORBIDDEN(403, "禁止访问"),

    NOT_FOUND(404, "您要找的资源不见啦，有可能被外星人抓走了..."),

    PARAM_INVALID(415, "无效的请求参数"),

    FAILED_DEPENDENCY(424, "请求令牌已过期"),

    ERROR(RConstants.FAIL, "服务器又在偷懒了，请稍后重试"),
    ;


    private final int code;

    private final String msg;


    GlobalErrorStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
