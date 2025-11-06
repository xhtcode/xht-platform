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
     * 是否是匿名接口
     * <li>true：是  不需要额外的认证，</li>
     * <li>false：否 需要额外的认证</li>
     */
    boolean isAnonymous() default true;

}
