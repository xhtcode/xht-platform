package com.xht.framework.log.annotations;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author xht
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BLog {

    /**
     * @return 名称
     */
    String value();

    /**
     * @return 描述
     */
    String description();

}
