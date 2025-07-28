package com.xht.framework.core.utils;

import com.xht.framework.core.domain.bo.ValidationExceptionBo;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 描述 ：jsr303 异常校验
 *
 * @author : xht
 **/
@Slf4j
@SuppressWarnings("unused")
public final class ValidationUtils {

    /**
     * 校验对象
     *
     * @param object 校验对象
     * @param groups 分组
     */
    public static <T> void validate(T object, Class<?>... groups) {
        ValidatorFactory vf = null;
        try {
            vf = Validation.buildDefaultValidatorFactory();
            Validator validator = vf.getValidator();
            Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
            for (ConstraintViolation<T> violation : validate) {
                throw new ValidationException(String.valueOf(violation.getPropertyPath()), violation.getMessage());
            }
        } catch (ValidationException e) {
            throw new ValidationException(e.getField(), e.getMessage());
        } catch (Exception e) {
            log.error("校验工具加载异常:{}", e.getMessage(), e);
            throw new UtilException(e.getMessage(), e);
        } finally {
            if (Objects.nonNull(vf)) {
                vf.close();
            }
        }
    }


    /**
     * 校验对象
     *
     * @param object 校验对象
     * @param groups 分组
     */
    public static <T> void validate(T object, Consumer<ValidationExceptionBo> consumer, Class<?>... groups) {
        ValidatorFactory vf = null;
        try {
            vf = Validation.buildDefaultValidatorFactory();
            Validator validator = vf.getValidator();
            Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
            for (ConstraintViolation<T> violation : validate) {
                consumer.accept(new ValidationExceptionBo(String.valueOf(violation.getPropertyPath()), violation.getMessage()));
            }
        } catch (ValidationException e) {
            throw new ValidationException(e.getField(), e.getMessage());
        } catch (Exception e) {
            log.error("校验工具加载异常:{}", e.getMessage(), e);
            throw new UtilException(e.getMessage(), e);
        } finally {
            if (Objects.nonNull(vf)) {
                vf.close();
            }
        }
    }
}
