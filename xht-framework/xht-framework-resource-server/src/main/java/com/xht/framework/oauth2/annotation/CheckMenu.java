package com.xht.framework.oauth2.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解： 菜单权限校验
 *
 * @author xht
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@oauth2.hasMenuCode('{value}'.split(','))")
public @interface CheckMenu {

    /**
     * 菜单权限
     */
    String[] value() default {};


}


