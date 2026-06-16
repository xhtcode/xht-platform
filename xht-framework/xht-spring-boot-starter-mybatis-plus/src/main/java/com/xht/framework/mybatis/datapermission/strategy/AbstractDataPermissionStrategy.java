package com.xht.framework.mybatis.datapermission.strategy;

import com.xht.framework.mybatis.datapermission.DataPermissionBO;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import org.springframework.core.Ordered;

/**
 * 描述： 数据权限策略抽象类
 *
 * @author xht
 **/
@Slf4j
public abstract class AbstractDataPermissionStrategy implements Ordered {

    /**
     * 执行 INSERT 语句时的数据权限过滤逻辑
     *
     * @param dataPermissionBO  数据权限业务对象，包含权限类型与权限列定义
     * @return Expression 新的 where 条件
     */
    public Expression executeInsert(DataPermissionBO dataPermissionBO) throws Exception {
        return null;
    }

    /**
     * 执行 DELETE 语句时的数据权限过滤逻辑
     *
     * @param dataPermissionBO  数据权限业务对象，包含权限类型与权限列定义
     * @return Expression 新的 where 条件
     */
    public Expression executeDelete(DataPermissionBO dataPermissionBO) throws Exception {
        return null;
    }

    /**
     * 执行 UPDATE 语句时的数据权限过滤逻辑
     *
     * @param dataPermissionBO  数据权限业务对象，包含权限类型与权限列定义
     * @return Expression 新的 where 条件
     */
    public Expression executeUpdate(DataPermissionBO dataPermissionBO) throws Exception {
        return null;
    }

    /**
     * 执行 SELECT 语句时的数据权限过滤逻辑
     *
     * @param dataPermissionBO  数据权限业务对象，包含权限类型与权限列定义
     * @return Expression 新的 where 条件
     */
    public Expression executeSelect(DataPermissionBO dataPermissionBO) throws Exception {
        return null;
    }

    /**
     * 是否支持
     * @param permissionType 数据权限类型
     * @param mappedStatementId 映射语句ID
     * @return 是否支持
     */
    public abstract boolean support(String permissionType, String mappedStatementId);


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
