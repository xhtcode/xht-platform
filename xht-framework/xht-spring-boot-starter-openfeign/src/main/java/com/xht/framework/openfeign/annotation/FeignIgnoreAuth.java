package com.xht.framework.openfeign.annotation;

import java.lang.annotation.*;

/**
 * feign 忽略权限认证注解
 * 配合<code> com.xht.framework.security.annotation.IgnoreAuth</code>注解一起使用，
 *
 * @author xht
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeignIgnoreAuth {
}
