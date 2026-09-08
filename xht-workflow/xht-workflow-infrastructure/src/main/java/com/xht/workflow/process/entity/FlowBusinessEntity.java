package com.xht.workflow.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程业务
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_business")
public class FlowBusinessEntity extends BasicEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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
     * 事项类型：cate分类  order申请单据
     */
    @TableField(value = "item_type")
    private FlowDefinitionTypeEnum itemType;

    /**
     * 流程业务号
     */
    @TableField(value = "business_number")
    private String businessNumber;

}
