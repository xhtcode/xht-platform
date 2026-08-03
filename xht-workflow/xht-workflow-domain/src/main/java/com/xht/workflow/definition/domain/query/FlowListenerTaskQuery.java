package com.xht.workflow.definition.domain.query;

import com.xht.workflow.definition.enums.ListenerTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 流程扩展-任务监听器查询请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-任务监听器查询请求参数")
public class FlowListenerTaskQuery extends FlowListenerBasicQuery<ListenerTypeEnums> {

}
