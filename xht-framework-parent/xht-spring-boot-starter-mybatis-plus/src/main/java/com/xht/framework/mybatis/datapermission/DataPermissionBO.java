package com.xht.framework.mybatis.datapermission;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.mybatis.datapermission.annoataion.DataPermission;
import com.xht.framework.mybatis.datapermission.annoataion.DataPermissions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：数据权限 业务类
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataPermissionBO {

    /**
     * 数据权限类别标识，用于区分不同的数据权限规则类型
     */
    private final String permissionType;

    /**
     * 是否忽略数据权限
     */
    private final boolean ignore;

    /**
     * 数据权限列集合，定义了需要应用数据权限过滤的数据库列信息
     */
    private final List<DataPermissionColumnBO> columns;

    /**
     * 获取数据权限列集合中的第一个元素
     *
     * @return 数据权限列集合中的第一个元素，如果集合为空则返回 null
     */
    public DataPermissionColumnBO getFirstColumn() {
        if (columns == null || columns.isEmpty()) {
            throw new BusinessException("Columns cannot be null or empty");
        }
        return columns.get(0);
    }


    /**
     * 创建 {@link Builder} 实例，用于链式构建 {@link DataPermissionBO} 对象
     *
     * @return 新的 Builder 实例
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@link DataPermissionBO} 的构建器，支持链式调用方式逐步设置属性并构建目标对象
     */
    public static class Builder {

        /**
         * 数据权限类别标识
         */
        private String permissionType;
        /**
         * 是否忽略数据权限
         */
        private boolean ignore;
        /**
         * 数据权限列集合
         */
        private List<DataPermissionColumnBO> columns;

        /**
         * 从 {@link DataPermissions} 注解中提取数据权限配置信息，填充到构建器中
         *
         * @param dataPermissions 数据权限注解实例，包含权限类型和权限列定义
         * @return 当前 Builder 实例，支持链式调用
         */
        public Builder of(DataPermissions dataPermissions) {
            this.permissionType = dataPermissions.type();
            this.ignore = dataPermissions.ignore();
            this.columns = new ArrayList<>();
            for (DataPermission dataPermission : dataPermissions.value()) {
                columns.add(new DataPermissionColumnBO(dataPermission.type(), dataPermission.tableAlias(), dataPermission.columnName()));
            }
            return this;
        }

        /**
         * 构建并返回 {@link DataPermissionBO} 实例
         *
         * @return 使用当前 Builder 属性值构建的 DataPermissionBO 对象
         */
        public DataPermissionBO build() {
            return new DataPermissionBO(this.permissionType, this.ignore, this.columns);
        }
    }

}
