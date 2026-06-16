package com.xht.framework.mybatis.datapermission;

import com.xht.framework.mybatis.datapermission.annoataion.Column;
import com.xht.framework.mybatis.datapermission.annoataion.DataPermission;
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
         * 从 {@link DataPermission} 注解中提取数据权限配置信息，填充到构建器中
         *
         * @param dataPermission 数据权限注解实例，包含权限类型和权限列定义
         * @return 当前 Builder 实例，支持链式调用
         */
        public Builder of(DataPermission dataPermission) {
            this.permissionType = dataPermission.type();
            this.ignore = dataPermission.ignore();
            this.columns = new ArrayList<>();
            for (Column column : dataPermission.value()) {
                columns.add(new DataPermissionColumnBO(column.tableAlias(), column.columnName()));
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
