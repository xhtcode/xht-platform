package com.xht.framework.core.validation.enums;

import com.xht.framework.core.enums.XhtEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 枚举校验
 *
 * @author xht
 **/
public class EnumsValidator implements ConstraintValidator<Enums, XhtEnum<?>> {

    private static final Set<Object> VALUE_SET = new HashSet<>();

    @Override
    public void initialize(Enums enums) {
        Class<? extends XhtEnum<?>> value = enums.value();
        XhtEnum<?>[] enumConstants = value.getEnumConstants();
        for (XhtEnum<?> enumConstant : enumConstants) {
            Optional.ofNullable(enumConstant.getValue()).ifPresent(VALUE_SET::add);
        }
    }

    /**
     * 校验枚举值
     *
     * @param var1 枚举值
     * @param var2 验证上下文
     * @return 校验结果
     */
    @Override
    public boolean isValid(XhtEnum<?> var1, ConstraintValidatorContext var2) {
        if (Objects.isNull(var1)) {
            return false;
        }
        return VALUE_SET.contains(var1.getValue());
    }
}
