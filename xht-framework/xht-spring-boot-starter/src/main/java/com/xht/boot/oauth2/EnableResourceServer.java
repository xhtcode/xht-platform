package com.xht.boot.oauth2;

import com.xht.framework.oauth2.configurers.ResourceServerAutoConfiguration;
import com.xht.framework.oauth2.configurers.SecurityAutoConfiguration;
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
