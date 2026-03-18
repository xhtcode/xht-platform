package com.xht.framework.log.annotations;

import com.xht.framework.log.configurers.BLogCondition;
import com.xht.framework.log.configurers.BLogProperties;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 日志自动装配条件注解
 *
 * @author xht
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(BLogCondition.class)
public @interface ConditionalOnBLog {

    /**
     * 存储仓库类型
     *
     * @return 存储仓库类型
     */
    BLogProperties.RepositoryType value() default BLogProperties.RepositoryType.DEFAULT;
}
