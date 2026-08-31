package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.definition.enums.FlowDefinitionStatusEnum;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程事项定义
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_item_def")
public class FlowItemDefEntity extends BasicEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 事项父级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 事项层级
     */
    @TableField(value = "item_level")
    private Integer itemLevel;

    /**
     * 事项编码
     */
    @TableField(value = "item_code")
    private String itemCode;

    /**
     * 事项名称
     */
    @TableField(value = "item_name")
    private String itemName;

    /**
     * 事项类型：cate分类 order申请单据
     */
    @TableField(value = "item_type")
    private FlowDefinitionTypeEnum itemType;

    /**
     * 事项描述
     */
    @TableField(value = "item_desc")
    private String itemDesc;

    /**
     * 事项状态：0禁用 1正常
     */
    @TableField(value = "item_status")
    private FlowDefinitionStatusEnum itemStatus;

    /**
     * 事项排序号，数值越大越靠前
     */
    @TableField(value = "item_sort")
    private Integer itemSort;

}