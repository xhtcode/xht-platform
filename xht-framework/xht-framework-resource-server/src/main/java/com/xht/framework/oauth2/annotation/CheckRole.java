package com.xht.framework.oauth2.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解： 角色权限校验
 *
 * @author xht
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@oauth2.hasRoleCode('{value}'.split(','))")
public @interface CheckRole {

    /**
     * 角色权限
     */
    String[] value() default {};

}
