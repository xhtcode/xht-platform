package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;
import com.xht.workflow.definition.enums.DefinitionTypeEnum;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_definition")
public class FlowDefinitionEntity extends BasicEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 流程定义父级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 流程定义层级
     */
    @TableField(value = "definition_level")
    private Integer definitionLevel;

    /**
     * 流程定义编码
     */
    @TableField(value = "definition_code")
    private String definitionCode;

    /**
     * 流程定义名称
     */
    @TableField(value = "definition_name")
    private String definitionName;

    /**
     * 流程定义类型：cate分类  order申请单据
     */
    @TableField(value = "definition_type")
    private DefinitionTypeEnum definitionType;

    /**
     * 流程定义描述
     */
    @TableField(value = "definition_desc")
    private String definitionDesc;

    /**
     * 流程定义状态
     */
    @TableField(value = "definition_status")
    private DefinitionStatusEnum definitionStatus;

    /**
     * 流程定义排序
     */
    @TableField(value = "definition_sort")
    private Integer definitionSort;

}