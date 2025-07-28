package com.xht.framework.security.annotation;

import java.lang.annotation.*;


/**
 * 内部接口权限注解
 *
 * @author xht
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface InnerAuth {

    /**
     * 是否匿名认证
     * true：不需要认证，false：需要认证
     */
    boolean isAnonymous() default true;

}
