package com.xht.framework.openfeign.fallback;

import com.xht.framework.core.domain.R;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign fallback 基类
 *
 * @author xht
 **/
@Slf4j
@Getter
public abstract class BasicFallback {

    private final Throwable cause;

    /**
     * 构造函数，创建BasicFallback实例并记录错误日志
     *
     * @param cause 异常原因
     */
    public BasicFallback(Throwable cause) {
        this.cause = cause;
    }

    /**
     * 返回错误响应，使用构造函数中传入的异常消息
     *
     * @param <T> 泛型类型
     * @return 包含异常消息的错误响应对象
     */
    protected final <T> R<T> error() {
        return R.error().msg(cause.getMessage()).build();
    }

    /**
     * 返回错误响应，使用指定的错误消息
     *
     * @param <T>     泛型类型
     * @param message 自定义错误消息
     * @return 包含指定消息的错误响应对象
     */
    protected final <T> R<T> error(String message) {
        return R.error().msg(message).build();
    }

}
