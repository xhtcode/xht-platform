package com.xht.framework.core.exception.code;

import lombok.Getter;

/**
 * 用户级别的错误码（用户引起的错误返回码，可以不用关注）
 *
 * @author xht
 */
@Getter
public enum UserErrorCode implements ErrorCode {

    /**
     * 用户名或密码错误
     */
    PASSWORD_ERROR(30001, "用户名或密码错误"),

    /**
     * 用户被锁定
     */
    USER_LOCKED(30002, "用户被锁定"),

    /**
     * 账号未注册或已注销
     */
    DATA_NOT_EXIST(30003, "账号未注册或已注销");

    private final int code;

    private final String msg;


    UserErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
