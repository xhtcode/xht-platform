package com.xht.framework.oauth2.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 资源服务器注解
 *
 * @author xht
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {SecurityAutoConfiguration.class, ResourceServerAutoConfiguration.class})
public @interface EnableResourceServer {

}
