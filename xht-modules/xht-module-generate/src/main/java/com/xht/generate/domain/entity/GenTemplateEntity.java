package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-代码生成模板表（区分文件类型）
 */
@TableName(value ="gen_template")
@Data
public class GenTemplateEntity extends BasicEntity implements Serializable {
    /**
     * 模板ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板内容（Velocity/FreeMarker语法）
     */
    private String content;

    /**
     * 文件类型（java/ts/vue/xml/sql等）
     */
    private String fileType;

    /**
     * 文件路径模板（如：src/main/java/{{package}}）
     */
    private String filePathTemplate;

    /**
     * 文件名模板（如：{{className}}Controller.java）
     */
    private String fileNameTemplate;




}