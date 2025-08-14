package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.framework.mybatis.extension.handlers.JsonTypeHandler;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.domain.ColumnExtConfig;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-表字段信息表（支持扩展配置）
 * @author xht
 */
@Data
@TableName(value ="gen_column_info")
public class GenColumnInfoEntity extends BasicEntity implements Serializable {

    /**
     * 字段ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 表ID（关联gen_table_info）
     */
    @TableField(value = "table_id")
    private String tableId;

    /**
     * 数据库字段名
     */
    @TableField(value = "column_name")
    private String columnName;

    /**
     * 数据库字段类型
     */
    @TableField(value = "db_data_type")
    private String dbDataType;

    /**
     * 字段注释
     */
    @TableField(value = "column_comment")
    private String columnComment;

    /**
     * 字段长度
     */
    @TableField(value = "column_length")
    private int columnLength;

    /**
     * 字段代码名称
     */
    @TableField(value = "code_name")
    private String codeName;

    /**
     * 字段代码注释
     */
    @TableField(value = "code_comment")
    private String codeComment;

    /**
     * 是否必填（0否 1是）
     */
    @TableField(value = "is_required")
    private GenStatusEnums isRequired;

    /**
     * 是否主键（0否 1是）
     */
    @TableField(value = "is_primary")
    private GenStatusEnums isPrimary;

    /**
     * 配置属性
     */
    @TableField(value = "ext_config", typeHandler = JsonTypeHandler.class)
    private ColumnExtConfig extConfig;

    /**
     * 字段排序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

}