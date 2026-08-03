package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.workflow.definition.enums.ListenerScriptTypeEnum;
import com.xht.workflow.definition.enums.ListenerStatusEnum;
import com.xht.workflow.definition.enums.ListenerTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述：流程扩展-执行监听器响应参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-执行监听器响应参数")
public class FlowListenerBasicResponse<T extends XhtEnum<String>> extends MetaResponse {

    /**
     * 序列id
     */
    @Schema(name = "序列id")
    private Long id;

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
     * java类
     */
    @Schema(description = "java类")
    private String javaClass;

    /**
     * 表达式
     */
    @Schema(description = "表达式")
    private String expressionValue;

    /**
     * 代理表达式
     */
    @Schema(description = "代理表达式")
    private String delegateExpression;

    /**
     * 脚本格式
     */
    @Schema(description = "脚本格式")
    private String scriptFormat;

    /**
     * 脚本类型
     */
    @Schema(description = "脚本类型")
    private ListenerScriptTypeEnum scriptType;

    /**
     * 内联脚本内容
     */
    @Schema(description = "内联脚本内容")
    private String scriptContent;

    /**
     * 外部脚本资源路径
     */
    @Schema(description = "外部脚本资源路径")
    private String scriptResource;

    /**
     * 监听器顺序
     */
    @Schema(description = "监听器顺序")
    private Integer listenerOrder;

    /**
     * 监听器状态
     */
    @Schema(description = "监听器状态")
    private ListenerStatusEnum listenerStatus;

    /**
     * 监听器描述
     */
    @Schema(description = "监听器描述")
    private String listenerDesc;

}
