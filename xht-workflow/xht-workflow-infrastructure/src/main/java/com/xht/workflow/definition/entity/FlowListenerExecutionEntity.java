package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.workflow.definition.enums.ListenerExecutionEventTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程扩展-执行监听器
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_listener_execution")
public class FlowListenerExecutionEntity extends FlowListenerBasicEntity<ListenerExecutionEventTypeEnum> implements Serializable {

}
