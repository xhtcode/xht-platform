package com.xht.framework.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：标签值对象
 * @author xht
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class LabelValue<K extends Serializable, V extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 绑定值
     * 泛型设计，支持多类型，无需强制类型转换
     */
    private K value;

    /**
     * 展示文本
     */
    private V label;

    /**
     * 静态工厂方法，快速创建 LabelValue 实例（比new更简洁）
     * 示例：LabelValue.of("启用", 1)
     *
     * @param label 展示文本
     * @param value 绑定值
     * @return LabelValue 实例
     */
    public static <K extends Serializable, V extends Serializable> LabelValue<K, V> of(K value, V label) {
        return new LabelValue<>(value, label);
    }

}
