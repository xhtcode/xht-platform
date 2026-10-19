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
     * 是否校验内部调用头
     *
     * <li>true（默认）：内部接口，校验 {@code xht.security.header.auth-key/auth-value}</li>
     * <li>false：对外开放接口，仅放行、不校验内部调用头</li>
     *
     * <p>标在类上时只负责把 URL 加入白名单，不触发内部头校验；需要校验的接口请标在方法上。</p>
     */
    boolean aop() default true;

}
