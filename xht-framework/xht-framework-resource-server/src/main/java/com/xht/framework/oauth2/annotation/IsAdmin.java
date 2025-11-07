package com.xht.framework.oauth2.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解： 检测用户是否是管理员
 *
 * @author xht
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@oauth2.isAdmin()")
public @interface IsAdmin {

}
