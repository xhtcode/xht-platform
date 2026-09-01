package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.NoneDeleteEntity;
import com.xht.framework.common.enums.DefaultStatusEnum;
import com.xht.framework.common.enums.EnableStatusEnum;
import com.xht.workflow.definition.enums.ProcStartTypeEnum;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_item_process")
public class FlowItemProcessEntity extends NoneDeleteEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事项定义主键id
     */
    @TableField(value = "item_def_id")
    private Long itemDefId;

    /**
     * 路由主键
     */
    @TableField(value = "router_key")
    private String routerKey;

    /**
     * 路由名称
     */
    @TableField(value = "router_name")
    private String routerName;

    /**
     * 流程启动方式id或者key
     */
    @TableField(value = "proc_start_type")
    private ProcStartTypeEnum procStartType;

    /**
     * 流程定义id
     */
    @TableField(value = "proc_def_id")
    private String procDefId;

    /**
     * 流程定义key
     */
    @TableField(value = "proc_def_key")
    private String procDefKey;

    /**
     * 流程定义名称
     */
    @TableField(value = "proc_def_name")
    private String procDefName;

    /**
     * 流程定义版本
     */
    @TableField(value = "proc_def_version")
    private Integer procDefVersion;

    /**
     * 匹配优先级
     */
    @TableField(value = "proc_def_priority")
    private Integer procDefPriority;

    /**
     * 流程部署id
     */
    @TableField(value = "deployment_id")
    private String deploymentId;

    /**
     * 默认状态
     */
    @TableField(value = "default_status")
    private DefaultStatusEnum defaultStatus;

    /**
     * 启用状态
     */
    @TableField(value = "enable_status")
    private EnableStatusEnum enableStatus;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
