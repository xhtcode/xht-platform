package com.xht.workflow.definition.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.workflow.definition.enums.ListenerStatusEnum;
import com.xht.workflow.definition.enums.ListenerTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述：流程扩展-执行监听器请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-执行监听器请求参数")
public class FlowListenerBasicQuery<T extends XhtEnum<String>> extends PageBasicQuery {

    /**
     * 事件类型
     */
    @Schema(description = "事件类型")
    private T eventType;

    /**
     * 监听器类型
     */
    @Schema(description = "监听器类型")
    private ListenerTypeEnums listenerType;

    /**
     * 监听器状态
     */
    @Schema(description = "监听器状态")
    private ListenerStatusEnum listenerStatus;

}
