package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 代码生成器-表字段信息表（支持扩展配置）
 * @TableName gen_column_info
 */
@TableName(value ="gen_column_info")
@Data
public class GenColumnInfoEntity extends BasicEntity implements Serializable {
    /**
     * 字段ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 表ID（关联gen_table_info）
     */
    private Long tableId;

    /**
     * 数据库字段名
     */
    private String columnName;

    /**
     * 数据库字段类型
     */
    private String dbDataType;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 字段自定义配置（JSON格式），示例：{"codeFieldName":"userId","codeFieldLabel":"用户ID","isQuery":1,"queryType":"eq","isList":1,"isAdd":1,"isEdit":1,"isView":1}
     */
    private Object extConfig;

    /**
     * 字段默认值
     */
    private String defaultValue;

    /**
     * 是否必填（0否 1是）
     */
    private Integer isRequired;

    /**
     * 是否主键（0否 1是）
     */
    private Integer isPrimary;

    /**
     * 是否外键（0否 1是）
     */
    private Integer isForeign;

    /**
     * 外键关联表名
     */
    private String foreignTable;

    /**
     * 外键关联字段名
     */
    private String foreignColumn;

    /**
     * 字段排序
     */
    private Integer sortOrder;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}