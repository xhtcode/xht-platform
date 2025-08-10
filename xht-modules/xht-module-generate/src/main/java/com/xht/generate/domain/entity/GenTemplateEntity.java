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
 * @author xht
 */
@Data
@TableName(value = "gen_template")
public class GenTemplateEntity extends BasicEntity implements Serializable {

    /**
     * 模板ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组id
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 模板名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 模板内容（Velocity/FreeMarker语法）
     */
    @TableField(value = "content")
    private String content;

    /**
     * 文件类型（java/ts/vue/xml/sql等）
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 文件路径模板（如：src/main/java/{{package}}）
     */
    @TableField(value = "file_path_template")
    private String filePathTemplate;

    /**
     * 文件名模板（如：{{className}}Controller.java）
     */
    @TableField(value = "file_name_template")
    private String fileNameTemplate;


}