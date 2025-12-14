package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.NoneDeleteEntity;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-表字段信息表
 * @author xht
 */
@Data
@TableName(value ="gen_table_column")
public class GenTableColumnEntity extends NoneDeleteEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 表id
     */
    @TableField(value = "table_id")
    private Long tableId;

    /**
     * 表名称(冗余字段)
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 字段名
     */
    @TableField(value = "db_name")
    private String dbName;

    /**
     * 字段类型
     */
    @TableField(value = "db_type")
    private String dbType;

    /**
     * 字段主键：0-非主键，1-主键
     */
    @TableField(value = "db_primary")
    private IdPrimaryKeyEnums dbPrimary;

    /**
     * 字段必填：0-非必填，1-必填
     */
    @TableField(value = "db_required")
    private GenStatusEnums dbRequired;

    /**
     * 字段注释
     */
    @TableField(value = "db_comment")
    private String dbComment;

    /**
     * 字段长度
     */
    @TableField(value = "db_length")
    private int dbLength;

    /**
     * 代码名称
     */
    @TableField(value = "code_name")
    private String codeName;

    /**
     * 代码注释
     */
    @TableField(value = "code_comment")
    private String codeComment;

    /**
     * 表单新增：0-不显示，1-显示
     */
    @TableField(value = "from_insert")
    private GenStatusEnums fromInsert;

    /**
     * 表单更新：0-不显示，1-显示
     */
    @TableField(value = "from_update")
    private GenStatusEnums fromUpdate;

    /**
     * 表单输入长度
     */
    @TableField(value = "from_length")
    private Integer fromLength;

    /**
     * 表单必填：0-非必填，1-必填
     */
    @TableField(value = "from_fill")
    private GenStatusEnums fromFill;

    /**
     * 表单组件
     */
    @TableField(value = "from_component")
    private String fromComponent;

    /**
     * 列表显示：0-不显示，1-显示
     */
    @TableField(value = "list_show")
    private GenStatusEnums listShow;

    /**
     * 显示切换禁用：0-不禁用，1-禁用
     */
    @TableField(value = "list_disabled")
    private GenStatusEnums listDisabled;

    /**
     * 默认隐藏：0-不隐藏，1-隐藏
     */
    @TableField(value = "list_hidden")
    private GenStatusEnums listHidden;

    /**
     * java类型
     */
    @TableField(value = "code_java")
    private String codeJava;

    /**
     * java类型 包地址
     */
    @TableField(value = "code_java_package")
    private String codeJavaPackage;

    /**
     * ts类型
     */
    @TableField(value = "code_ts")
    private String codeTs;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字段排序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

}