package com.xht.platform.dict.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * 系统字典校验注解
 * <p>
 * 使用前提：所在服务必须引入 {@code xht-platform-client-dict}，该模块负责注册
 * {@link com.xht.platform.common.dict.factory.ISysDictFactory}；未引入时本注解不可用（编译期即报错）。
 * <p>
 * 值为 {@code null} 或空白时放行，字段必填请配合 {@code @NotBlank} 使用；
 * 字典项 {@code disabled = true} 视为不合法。
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
    String message() default "字典项不存在或已禁用";

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
