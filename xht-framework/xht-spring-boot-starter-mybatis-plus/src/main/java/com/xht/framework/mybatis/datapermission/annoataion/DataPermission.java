package com.xht.framework.mybatis.datapermission.annoataion;

import java.lang.annotation.*;

/**
 * 描述：数据权限
 *
 * @author xht
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataPermission {

    /**
     * 数据权限类别
     * @return 数据权限类别
     */
    String type();

    /**
     * 是否忽略数据权限
     * @return 是否忽略数据权限
     */
    boolean ignore() default false;

    /**
     * 权限列
     * @return 权限列
     */
    Column[] value();

}
