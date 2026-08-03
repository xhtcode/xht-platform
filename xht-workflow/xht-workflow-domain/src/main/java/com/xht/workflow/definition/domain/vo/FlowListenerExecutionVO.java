package com.xht.workflow.definition.domain.vo;

import com.xht.framework.common.domain.vo.XhtVO;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.domain.response.FlowListenerFieldResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述：流程扩展-执行监听器VO
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-执行监听器VO")
public class FlowListenerExecutionVO implements XhtVO {

    /**
     * 执行监听器
     */
    @Schema(description = "执行监听器")
    private FlowListenerExecutionResponse listener;

    /**
     * 监听器字段信息
     */
    @Schema(description = "监听器字段信息")
    private List<FlowListenerFieldResponse> fields;

}
