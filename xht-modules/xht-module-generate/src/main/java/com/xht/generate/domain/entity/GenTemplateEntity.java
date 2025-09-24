package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.framework.mybatis.extension.handlers.JsonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 代码生成器-代码生成模板表（区分文件类型）
 * @author xht
 */
@Data
@TableName(value = "gen_template")
public class GenTemplateEntity extends BasicEntity implements Serializable {

    /**
     * 模板ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分组id
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 模板名称
     */
    @TableField(value = "template_name")
    private String templateName;

    /**
     * 模板内容
     */
    @TableField(value = "template_content")
    private String templateContent;

    /**
     * 文件路径模板
     */
    @TableField(value = "template_file_path")
    private String templateFilePath;

    /**
     * 文件名模板
     */
    @TableField(value = "template_file_name")
    private String templateFileName;

    /**
     * 文件类型
     */
    @TableField(value = "template_file_type")
    private String templateFileType;

    /**
     * 忽略的字段,逗号分割
     */
    @TableField(value = "template_ignore_field", typeHandler = JacksonTypeHandler.class)
    private List<String> templateIgnoreField;

    /**
     * 模板排序
     */
    @TableField(value = "template_sort")
    private Integer templateSort;
}