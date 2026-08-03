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
 * 流程分类
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_definition")
public class FlowDefinitionEntity extends BasicEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 类别父级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 类别层级
     */
    @TableField(value = "definition_level")
    private Integer definitionLevel;

    /**
     * 类别编码
     */
    @TableField(value = "definition_code")
    private String definitionCode;

    /**
     * 类别名称
     */
    @TableField(value = "definition_name")
    private String definitionName;

    /**
     * 类别类型：cate分类  order申请单据
     */
    @TableField(value = "definition_type")
    private DefinitionTypeEnum definitionType;

    /**
     * 类别描述
     */
    @TableField(value = "definition_desc")
    private String definitionDesc;

    /**
     * 类别状态
     */
    @TableField(value = "definition_status")
    private DefinitionStatusEnum definitionStatus;

    /**
     * 类别排序
     */
    @TableField(value = "definition_sort")
    private Integer definitionSort;

}