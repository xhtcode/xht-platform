package com.xht.framework.core.support.dict.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * 系统字典校验注解
 *
 * @author xht
 **/
@Constraint(validatedBy = {DictValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {

    /**
     * 字典编码
     *
     * @return 字典编码
     */
    String value();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String message() default "字典编码不存在";

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
