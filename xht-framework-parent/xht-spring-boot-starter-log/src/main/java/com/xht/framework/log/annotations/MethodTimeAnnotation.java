package com.xht.framework.log.annotations;


import java.lang.annotation.*;

/**
 * 描述 ：aop 方法计时 日志
 *
 * @author xht
 **/
@Documented
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodTimeAnnotation {

    /**
     * 接口名
     */
    String value() default "";

}
