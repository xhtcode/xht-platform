package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-表结构信息表
 */
@TableName(value ="gen_table_info")
@Data
public class GenTableInfoEntity extends BasicEntity implements Serializable {
    /**
     * 表ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 数据源ID
     */
    private Long dataSourceId;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 表注释（如：用户表）
     */
    private String tableComment;

    /**
     * 主键字段名
     */
    private String primaryKey;

    /**
     * 生成的类名（如：User）
     */
    private String codeName;

    /**
     * 代码的注释（如：用户）
     */
    private String codeComment;




}