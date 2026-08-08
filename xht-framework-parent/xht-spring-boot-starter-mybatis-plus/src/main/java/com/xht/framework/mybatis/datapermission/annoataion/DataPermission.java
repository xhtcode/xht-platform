package com.xht.framework.mybatis.datapermission.annoataion;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：权限列
 *
 * @author xht
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface DataPermission {

    /**
     * 数据权限类别
     * @return 数据权限类别
     */
    String type() default "";

    /**
     * 表别名
     *
     * @return 表别名
     */
    String tableAlias() default "";

    /**
     * 列名
     * @return 列名
     */
    String columnName();

}
