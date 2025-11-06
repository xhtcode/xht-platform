package com.xht.framework.security.annotation;

import java.lang.annotation.*;

/**
 * 接口权限注解
 *
 * @author xht
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IgnoreAuth {

    /**
     * 是否启用aop拦截
     *
     * <li>true：是  ，</li>
     * <li>false：否 </li>
     */
    boolean aop() default true;

}
