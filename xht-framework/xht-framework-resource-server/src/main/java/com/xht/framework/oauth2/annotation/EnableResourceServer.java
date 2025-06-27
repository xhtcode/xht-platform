package com.xht.framework.oauth2.annotation;

import com.xht.framework.oauth2.configurers.ResourceServerAutoConfiguration;
import com.xht.framework.oauth2.configurers.ResourceServerConfiguration;
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
@Import(value = {ResourceServerAutoConfiguration.class, ResourceServerConfiguration.class})
public @interface EnableResourceServer {

}
