package com.xht.framework.swagger.annotation;

import com.xht.framework.swagger.SpringDocAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableCustomSwagger
 *
 * @author xht
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringDocAutoConfiguration.class})
public @interface EnableCustomSwagger {
}
