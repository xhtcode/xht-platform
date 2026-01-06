package com.xht.modules.generate.domain.bo;

import lombok.Data;

/**
 * 查询字段业务对象
 *
 * @author xht
 **/
@Data
public class ColumnQueryBo {

    /**
     * 表id
     */
    private Long tableId;

    /**
     * 表名称(冗余字段)
     */
    private String tableName;

    /**
     * 字段id
     */
    private Long columnId;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 表单输入长度
     */
    private Integer fromLength;

    /**
     * 查询类型（如等于、不等于、大于、小于等）
     */
    private String queryType;

    /**
     * 条件标签（显示用的文本）
     */
    private String conditionLabel;

    /**
     * 字段值（条件值）
     */
    private String conditionValue;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典状态
     */
    private Boolean dictStatus;
    /**
     * 表单组件
     */
    private String fromComponent;



    /**
     * 字段名
     */
    private String dbName;

    /**
     * 字段类型
     */
    private String dbType;

    /**
     * 字段注释
     */
    private String dbComment;

    /**
     * 字段长度
     */
    private int dbLength;

    /**
     * 代码名称
     */
    private String codeName;

    /**
     * 代码名称首字母大写
     */
    private String codeNameUpperFirst;

    /**
     * 代码注释
     */
    private String codeComment;


    /**
     * java类型
     */
    private String codeJava;

    /**
     * java类型 包地址
     */
    private String codeJavaPackage;

    /**
     * ts类型
     */
    private String codeTs;


}
