package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-代码生成项目表（支持自定义配置）
 */
@TableName(value ="gen_project")
@Data
public class GenProjectEntity extends BasicEntity implements Serializable {
    /**
     * 项目ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 项目描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 配置元数据（定义控件类型/标签/选项等）
     */
    @TableField(value = "config_schema")
    private Object configSchema;

    /**
     * 自定义配置值（与config_schema对应）
     */
    @TableField(value = "custom_config")
    private Object customConfig;


}