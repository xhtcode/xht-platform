package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 代码生成器-项目与数据源关联表
 * @TableName gen_project_data_source
 */
@TableName(value ="gen_project_data_source")
@Data
public class GenProjectDataSourceEntity extends BasicEntity implements Serializable {
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
     * 数据源ID
     */
    private Long dataSourceId;

    /**
     * 是否默认数据源（0否 1是）
     */
    private Integer isDefault;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}