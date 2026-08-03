package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.workflow.definition.enums.ListenerFieldStatusEnum;
import com.xht.workflow.definition.enums.ListenerFieldTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述： 流程扩展-监听器（字段管理）响应结果
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-监听器（字段管理）响应信息")
public class FlowListenerFieldResponse extends MetaResponse {

    /**
     * 序列id
     */
    @Schema(description = "序列id")
    private Long id;

    /**
     * 监听器id
     */
    @Schema(description = "监听器id")
    private Long listenerId;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String fieldName;

    /**
     * 字段类型(字符串、表达式)
     */
    @Schema(description = "字段类型(字符串、表达式)")
    private ListenerFieldTypeEnum fieldType;

    /**
     * 字段值
     */
    @Schema(description = "字段值")
    private String fieldValue;

    /**
     * 字段状态
     */
    @Schema(description = "字段状态")
    private ListenerFieldStatusEnum fieldStatus;

}
