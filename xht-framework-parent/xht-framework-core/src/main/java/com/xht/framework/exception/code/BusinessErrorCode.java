package com.xht.framework.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误状态码（此类返回码应该高度重视）
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
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
    /**
     * 系统内置禁止操作
     */
    SYSTEM_FORBIDDEN(10006, "系统内置禁止操作"),
    /***
     * 非法操作
     */
    ILLEGAL_OPERATION(10007, "非法操作"),
    /**
     * 字典项不存在
     */
    DICT_ITEM_NOT_EXIST(10008, "字典项不存在"),
    /**
     * 字典不存在
     */
    DICT_NOT_EXIST(10009, "字典不存在"),
    /**
     * 按钮类型下不能添加子菜单、目录、按钮
     */
    BUTTON_TYPE_ERROR(10010, "按钮类型下不能添加子菜单、目录、按钮"),

    ;

    private final int code;

    private final String msg;


}

