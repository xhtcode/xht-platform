package com.xht.boot.swagger;

import com.xht.framework.swagger.configurers.SpringDocAutoConfiguration;
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
