package com.xht.workflow.definition.domain.query;

import com.xht.workflow.definition.enums.ListenerExecutionEventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 流程扩展-执行监听器查询请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-执行监听器查询请求参数")
public class FlowListenerExecutionQuery extends FlowListenerBasicQuery<ListenerExecutionEventTypeEnum> {

}
