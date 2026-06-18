package com.xht.framework.mybatis.datapermission;

/**
 * 描述：数据权限列业务类
 *
 * @author xht
 **/
public record DataPermissionColumnBO(String type, String tableAlias, String columnName) {

    /**
     * 获取SQL别名列名
     *
     * @return SQL别名列名
     */
    public String getSqlAliasColumnName() {
        return tableAlias + "." + columnName;
    }
}
