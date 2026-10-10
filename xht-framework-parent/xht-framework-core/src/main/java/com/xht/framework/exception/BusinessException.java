package com.xht.framework.exception;

import com.xht.framework.common.constant.RConstants;
import com.xht.framework.exception.code.ErrorCode;
import lombok.Getter;

/**
 * 自定义业务异常
 * <p>
 * 用于业务逻辑校验失败时抛出，区别于系统底层异常；
 * 实现ErrorCode接口，统一对外提供错误码和错误描述，配合全局异常处理器返回统一响应体。
 * 继承RuntimeException，属于运行时异常，无需手动throws声明，事务场景可触发回滚。
 *
 * @author xht
 */
@Getter
public class BusinessException extends RuntimeException implements ErrorCode {

    /**
     * 业务错误码
     * 注意：这是业务编码，不是HTTP响应状态码
     */
    private final int code;

    /**
     * 异常描述信息
     */
    private final String msg;

    /**
     * 构造器：仅传入异常消息
     * 使用默认失败错误码 {@link RConstants#FAIL}
     *
     * @param message 异常提示信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = RConstants.FAIL;
        this.msg = message;
    }

    /**
     * 构造器：传入异常消息 + 原始异常对象
     * 使用默认失败错误码 {@link RConstants#FAIL}，保留原始异常堆栈信息，便于问题排查
     *
     * @param message 异常提示信息
     * @param cause   底层原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = RConstants.FAIL;
        this.msg = message;
    }

    /**
     * 构造器：传入ErrorCode错误码对象
     * 推荐日常业务使用，统一使用预定义错误枚举，规范错误码与提示文案
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    /**
     * 构造器：传入ErrorCode错误码对象 + 原始异常对象
     * 用于捕获底层异常后包装为业务异常，保留原始堆栈，同时使用规范错误码
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     * @param cause     底层原始异常
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMsg(), cause);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    /**
     * 构造器：自定义错误码、提示信息、原始异常对象
     * 极少场景使用，优先使用ErrorCode枚举方式，避免硬编码错误码
     *
     * @param code    自定义业务错误码
     * @param message 异常提示信息
     * @param cause   底层原始异常
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }
}
