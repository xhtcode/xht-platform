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
 * @author xht
 */
@Data
@TableName(value = "gen_project")
public class GenTemplateGroupEntity extends BasicEntity implements Serializable {

    /**
     * 分组ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组名称
     */
    @TableField(value = "group_name")
    private String groupName;

    /**
     * 分组描述
     */
    @TableField(value = "group_desc")
    private String groupDesc;

}