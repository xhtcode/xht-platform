package com.xht.workflow.definition.domain.form;

import com.xht.workflow.definition.enums.ListenerTaskEventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 流程扩展-任务监听器表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-任务监听器表单请求参数")
public class FlowListenerTaskBasicForm extends FlowListenerBasicForm<ListenerTaskEventTypeEnum> {

}
