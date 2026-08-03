package com.xht.workflow.definition.domain.form;

import com.xht.workflow.definition.enums.ListenerExecutionEventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 流程扩展-执行监听器表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-执行监听器表单请求参数")
public class FlowListenerExecutionBasicForm extends FlowListenerBasicForm<ListenerExecutionEventTypeEnum> {

}
