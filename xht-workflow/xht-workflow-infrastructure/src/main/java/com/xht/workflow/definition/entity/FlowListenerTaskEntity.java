package com.xht.workflow.definition.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.workflow.definition.enums.ListenerTaskEventTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程扩展-任务监听器
 *
 * @author xht
 */
@Data
@TableName(value = "xht_flow_listener_task")
public class FlowListenerTaskEntity extends FlowListenerBasicEntity<ListenerTaskEventTypeEnum> implements Serializable {

}
