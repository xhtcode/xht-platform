package com.xht.framework.core.validation.enums;

import com.xht.framework.core.enums.XhtEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

/**
 * 枚举校验
 *
 * @author xht
 **/
public class EnumsValidator implements ConstraintValidator<Enums, XhtEnum<?>> {

    private XhtEnum<?>[] enumConstants;

    @Override
    public void initialize(Enums enums) {
        Class<? extends XhtEnum<?>> value = enums.value();
        this.enumConstants = value.getEnumConstants();
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
        if (Objects.isNull(var1) || Objects.isNull(enumConstants)) {
            return false;
        }
        for (XhtEnum<?> enumConstant : enumConstants) {
            if (Objects.equals(var1, enumConstant.getValue())) {
                return true;
            }
        }
        return false;
    }
}
