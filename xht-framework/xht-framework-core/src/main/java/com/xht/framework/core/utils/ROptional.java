package com.xht.framework.core.utils;

import com.xht.framework.core.constant.basic.RConstants;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.DataTypeEnums;
import com.xht.framework.core.exception.BusinessException;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 响应式Optional容器，用于安全处理可能为空的R<T>对象
 *
 * @param <T> 泛型数据类型
 * @author xht
 */
@Data
public final class ROptional<T> {

    private final R<T> value;

    private final Integer code;

    private final String msg;

    private final Boolean ok;

    private final T data;

    private final DataTypeEnums dataType;

    private ROptional(R<T> value) {
        if (value == null) {
            value = R.error().msg("R value cannot be null").build();
        }
        this.value = value;
        this.code = value.getCode();
        this.msg = value.getMsg();
        this.ok = value.getOk();
        this.data = value.getData();
        this.dataType = value.getDataType();
    }


    // ---------- 静态工厂方法 ----------
    public static <T> ROptional<T> of(R<T> value) {
        return new ROptional<>(value);
    }

    public static <T> ROptional<T> empty() {
        return new ROptional<>(null);
    }

    // ---------- 值存在性检查 ----------
    public void ifPresent(Consumer<T> consumer) {
        if (isSuccess()) {
            consumer.accept(data);
        }
    }

    // ---------- 安全获取值 ----------
    public Optional<T> get() {
        if (isSuccess()) {
            return Optional.ofNullable(value.getData());
        }
        throw new BusinessException("data is error!");
    }


    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isSuccess()) {
            return value.getData();
        } else {
            throw exceptionSupplier.get();
        }
    }


    // ---------- 响应状态检查 ----------

    /**
     * 判断当前操作是否成功
     *
     * @return true：成功，false：失败
     */
    public boolean isSuccess() {
        return Objects.equals(ok, Boolean.TRUE) && Objects.equals(code, RConstants.SUCCESS);
    }

}