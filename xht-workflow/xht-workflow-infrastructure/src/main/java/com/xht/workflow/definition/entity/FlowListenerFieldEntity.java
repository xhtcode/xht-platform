package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.definition.enums.ListenerFieldStatusEnum;
import com.xht.workflow.definition.enums.ListenerFieldTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：  流程扩展-监听器（字段管理）
 *
 * @author xht
 **/
@Data
@TableName(value = "xht_flow_listener_field")
public class FlowListenerFieldEntity extends BasicEntity implements Serializable {

    /**
     * 序列id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 监听器id
     */
    @TableField(value = "listener_id")
    private Long listenerId;

    /**
     * 字段名称
     */
    @TableField(value = "field_name")
    private String fieldName;

    /**
     * 字段类型(字符串、表达式)
     */
    @TableField(value = "field_type")
    private ListenerFieldTypeEnum fieldType;

    /**
     * 字段值
     */
    @TableField(value = "field_value")
    private String fieldValue;

    /**
     * 字段排序
     */
    @TableField(value = "field_order")
    private Integer fieldOrder;

    /**
     * 字段状态
     */
    @TableField(value = "field_status")
    private ListenerFieldStatusEnum fieldStatus;

}