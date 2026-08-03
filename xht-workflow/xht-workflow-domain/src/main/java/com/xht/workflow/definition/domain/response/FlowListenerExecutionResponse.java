package com.xht.workflow.definition.domain.response;

import com.xht.workflow.definition.enums.ListenerExecutionEventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 流程扩展-执行监听器响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-执行监听器响应信息")
public class FlowListenerExecutionResponse extends FlowListenerBasicResponse<ListenerExecutionEventTypeEnum> {

}
