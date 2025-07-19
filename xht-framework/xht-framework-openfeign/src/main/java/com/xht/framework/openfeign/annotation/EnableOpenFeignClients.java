package com.xht.framework.openfeign.annotation;

import com.xht.framework.openfeign.configurers.BasicOpenFeignAutoConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 自定义注解，继承 {@link EnableFeignClients} 注解，并添加 {@link Import} 注解导入 {@link FeignAutoConfiguration} 配置类。
 *
 * @author xht
 **/
@Inherited
@Documented
@SuppressWarnings("all")
@EnableFeignClients
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({BasicOpenFeignAutoConfig.class})
public @interface EnableOpenFeignClients {

    String[] value() default {};


    @AliasFor(annotation = EnableFeignClients.class, attribute = "basePackages")
    String[] basePackages() default {};
}
