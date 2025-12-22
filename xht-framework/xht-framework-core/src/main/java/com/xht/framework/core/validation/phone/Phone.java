package com.xht.framework.core.validation.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * 手机号格式校验注解
 *
 * @author xht
 **/
@Constraint(validatedBy = {PhoneValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Phone {

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String message() default "手机号格式不合法";

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
