package com.xht.framework.exception;

import com.xht.framework.common.constant.RConstants;
import com.xht.framework.exception.core.ErrorCode;
import com.xht.framework.utils.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


/**
 * 自定义业务异常
 * <p>
 * 用于业务逻辑校验失败时抛出，区别于系统底层异常；
 * 实现ErrorCode接口，统一对外提供错误码和错误描述，配合全局异常处理器返回统一响应体；
 * 继承RuntimeException，属于运行时异常，无需手动throws声明，事务场景可触发回滚。
 * <p>
 * 错误码来源：默认错误码（500）或 {@link ErrorCode} 错误枚举，不支持硬编码自定义错误码；
 * 接收 {@link ErrorCode} 的构造器已做空指针判断，errorCode 为 null 时抛出 {@link NullPointerException}
 *
 * @author xht
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class BusinessException extends RuntimeException implements ErrorCode {

    /**
     * 业务错误码
     * 注意：这是业务编码，不是HTTP响应状态码
     */
    private int code;

    /**
     * 构造器：仅传入异常消息
     * 错误码默认为 500
     *
     * @param message 异常提示信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = RConstants.FAIL;
    }

    /**
     * 构造器：传入异常消息 + 原始异常对象
     * 错误码默认为 500，保留原始堆栈
     *
     * @param message 异常提示信息
     * @param cause   底层原始异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = RConstants.FAIL;
    }

    /**
     * 构造器：传入ErrorCode错误码对象
     * 推荐日常业务使用，统一使用预定义错误枚举，规范错误码与提示文案；
     * 错误码与提示信息均取自错误枚举
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     */
    public BusinessException(ErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode, "errorCode 不能为 null").getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 构造器：传入ErrorCode错误码对象 + 原始异常对象
     * 用于捕获底层异常后包装为业务异常，保留原始堆栈，同时使用规范错误码；
     * 错误码与提示信息均取自错误枚举
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     * @param cause     底层原始异常
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "errorCode 不能为 null").getMessage(), cause);
        this.code = errorCode.getCode();
    }

    /**
     * 构造器：传入ErrorCode错误码对象 + 自定义异常消息
     * 错误码取自错误枚举，提示信息使用自定义文案
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     * @param message   异常提示信息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(resolveMessage(errorCode, message));
        this.code = errorCode.getCode();
    }

    /**
     * 构造器：传入ErrorCode错误码对象 + 自定义异常消息 + 原始异常对象
     * 错误码取自错误枚举，提示信息使用自定义文案，保留原始堆栈
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     * @param message   异常提示信息
     * @param cause     底层原始异常
     */
    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(resolveMessage(errorCode, message), cause);
        this.code = errorCode.getCode();
    }

    /**
     * 解析最终使用的异常消息：自定义消息为空（null 或空白）时，回退使用错误枚举的提示信息
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     * @param message   自定义异常消息
     * @return 最终使用的异常消息
     */
    private static String resolveMessage(ErrorCode errorCode, String message) {
        return StringUtils.emptyToDefault(message, Objects.requireNonNull(errorCode, "errorCode 不能为 null").getMessage());
    }

}
