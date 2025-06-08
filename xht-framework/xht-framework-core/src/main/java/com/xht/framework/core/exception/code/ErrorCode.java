package com.xht.framework.core.exception.code;

/**
 * 错误码<br>
 * 一共分为三种： 1）系统错误、2）用户级别错误、3）未预期到的错误
 *
 * @author xht
 **/
public interface ErrorCode {

    /**
     * 错误码
     */
    int getCode();

    /**
     * 错误消息
     */
    String getMsg();

}