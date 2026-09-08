package com.xht.workflow.process.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.workflow.process.enums.FlowTimeLimitTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 流程扩展-流程时限
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程时限响应")
public class FlowTimeLimitResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 时限ID
     */
    @Schema(description = "时限ID")
    private Long id;

    /**
     * 业务id
     */
    @Schema(description = "业务id")
    private Long businessId;

    /**
     * 时限类型：natural自然日  workday工作日(跳过周末顺延)
     */
    @Schema(description = "时限类型")
    private FlowTimeLimitTypeEnum timeLimitType;

    /**
     * 时限天数
     */
    @Schema(description = "时限天数")
    private Integer timeLimitValue;

    /**
     * 时限开始时间
     */
    @Schema(description = "时限开始时间")
    private LocalDateTime startTime;

    /**
     * 到期时间
     */
    @Schema(description = "到期时间")
    private LocalDateTime dueTime;

    /**
     * 临期预警提前量(天)
     */
    @Schema(description = "临期预警提前量(天)")
    private Integer warnAhead;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
