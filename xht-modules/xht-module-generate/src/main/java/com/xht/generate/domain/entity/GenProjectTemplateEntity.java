package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 代码生成器-项目与模板关联表
 * @TableName gen_project_template
 */
@TableName(value ="gen_project_template")
@Data
public class GenProjectTemplateEntity extends BasicEntity implements Serializable {
    /**
     * 关联ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 模板排序
     */
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}