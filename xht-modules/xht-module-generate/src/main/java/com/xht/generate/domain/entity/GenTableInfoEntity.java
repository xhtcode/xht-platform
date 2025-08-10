package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成器-表结构信息表
 * @author xht
 */
@Data
@TableName(value = "gen_table_info")
public class GenTableInfoEntity extends BasicEntity implements Serializable {

    /**
     * 表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组id
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @TableField(value = "data_source_id")
    private Long dataSourceId;

    /**
     * 引擎名称
     */
    @TableField(value = "engine_name")
    private String engineName;

    /**
     * 数据库表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 表注释（如：用户表）
     */
    @TableField(value = "table_comment")
    private String tableComment;

    /**
     * 生成的类名（如：User）
     */
    @TableField(value = "code_name")
    private String codeName;

    /**
     * 代码的注释（如：用户）
     */
    @TableField(value = "code_comment")
    private String codeComment;

    /**
     * 创建时间
     */
    @TableField(value = "table_create_time")
    private LocalDateTime tableCreateTime;

    /**
     * 更新时间
     */
    @TableField(value = "table_update_time")
    private LocalDateTime tableUpdateTime;

}