package com.xht.workflow.definition.domain.vo;

import com.xht.framework.common.domain.vo.XhtVO;
import com.xht.workflow.definition.domain.response.FlowListenerFieldResponse;
import com.xht.workflow.definition.domain.response.FlowListenerTaskResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述： 流程扩展-任务监听器VO
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-任务监听器VO")
public class FlowListenerTaskVO implements XhtVO {

    /**
     * 任务监听器信息
     */
    @Schema(description = "任务监听器信息")
    private FlowListenerTaskResponse listener;

    /**
     * 监听器字段信息
     */
    @Schema(description = "监听器字段信息")
    private List<FlowListenerFieldResponse> fields;

}
