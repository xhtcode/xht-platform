package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.workflow.definition.enums.ListenerScriptTypeEnum;
import com.xht.workflow.definition.enums.ListenerStatusEnum;
import com.xht.workflow.definition.enums.ListenerTypeEnums;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 流程扩展-执行监听器实体类
 *
 * @author xht
 **/
@Data
public class FlowListenerBasicEntity<T extends XhtEnum<String>> extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序列id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 事件类型
     */
    @TableField(value = "event_type")
    private T eventType;

    /**
     * 监听器类型
     */
    @TableField(value = "listener_type")
    private ListenerTypeEnums listenerType;

    /**
     * java类
     */
    @TableField(value = "java_class")
    private String javaClass;

    /**
     * 表达式
     */
    @TableField(value = "expression_value")
    private String expressionValue;

    /**
     * 代理表达式
     */
    @TableField(value = "delegate_expression")
    private String delegateExpression;

    /**
     * 脚本格式
     */
    @TableField(value = "script_format")
    private String scriptFormat;

    /**
     * 脚本类型
     */
    @TableField(value = "script_type")
    private ListenerScriptTypeEnum scriptType;

    /**
     * 内联脚本内容
     */
    @TableField(value = "script_content")
    private String scriptContent;

    /**
     * 外部脚本资源路径
     */
    @TableField(value = "script_resource")
    private String scriptResource;

    /**
     * 监听器顺序
     */
    @TableField(value = "listener_order")
    private Integer listenerOrder;

    /**
     * 监听器状态
     */
    @TableField(value = "listener_status")
    private ListenerStatusEnum listenerStatus;

    /**
     * 监听器描述
     */
    @TableField(value = "listener_desc")
    private String listenerDesc;

}
