package com.xht.framework.core.exception.code;

import lombok.Getter;

/**
 * 系统错误状态码（此类返回码应该高度重视）
 *
 * @author xht
 */
@Getter
public enum BusinessErrorCode implements ErrorCode {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(10001, "系统似乎出现了点小问题"),

    /**
     * `参数错误`
     */
    PARAM_ERROR(10002, "参数错误"),

    /**
     * 数据已存在
     */
    DATA_EXIST(10003, "数据已存在"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(10004, "数据不存在"),

    /**
     * 数据类型错误
     */
    DATA_TYPE_ERROR(10005, "数据类型错误"),

    ;

    private final int code;

    private final String msg;


    BusinessErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

