package com.xht.framework.core.support.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;

import java.util.Map;
import java.util.Objects;

/**
 * 自定义Map属性访问器，让SpEL可以直接通过key访问Map中的值
 *
 * @author xht
 */
@SuppressWarnings("all")
public class MapPropertyAccessor extends ReflectivePropertyAccessor {

    @Override
    public boolean canRead(EvaluationContext context, Object target, String name) {
        // 支持Map类型的目标对象
        return target instanceof Map;
    }


    @Override
    public TypedValue read(EvaluationContext context, Object target, String name) {
        if (Objects.isNull(target)) {
            return new TypedValue(null);
        }
        Map<?, ?> map = (Map<?, ?>) target;
        Object value = map.get(name);
        return new TypedValue(value);
    }
}