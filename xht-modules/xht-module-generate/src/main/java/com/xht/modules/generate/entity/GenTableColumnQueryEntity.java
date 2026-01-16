package com.xht.modules.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.NoneDeleteEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-查询条件
 *
 * @author xht
 */
@Data
@TableName(value = "gen_table_column_query")
public class GenTableColumnQueryEntity extends NoneDeleteEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 字段id
     */
    @TableField(value = "column_id")
    private Long columnId;

    /**
     * 字段名称
     */
    @TableField(value = "column_name")
    private String columnName;

    /**
     * 表单输入长度
     */
    @TableField(value = "from_length")
    private Integer fromLength;

    /**
     * 查询类型（如等于、不等于、大于、小于等）
     */
    @TableField(value = "query_type")
    private String queryType;

    /**
     * 条件标签（显示用的文本）
     */
    @TableField(value = "condition_label")
    private String conditionLabel;

    /**
     * 字段值（字段命名）
     */
    @TableField(value = "condition_value")
    private String conditionValue;

    /**
     * 表单组件
     */
    @TableField(value = "from_component")
    private String fromComponent;

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