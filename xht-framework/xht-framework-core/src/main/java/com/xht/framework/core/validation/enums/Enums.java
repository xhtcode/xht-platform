package com.xht.framework.core.validation.enums;

import com.xht.framework.core.enums.XhtEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 枚举校验注解
 *
 * @author xht
 **/
@Constraint(validatedBy = {EnumsValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Enums {

    /**
     * 枚举类
     *
     * @return 枚举类
     */
    Class<? extends XhtEnum<?>> value();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String message() default "枚举校验失败";

    /**
     * 分组
     *
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     *
     * @return 负载
     */
    Class<? extends Payload>[] payload() default {};
}
