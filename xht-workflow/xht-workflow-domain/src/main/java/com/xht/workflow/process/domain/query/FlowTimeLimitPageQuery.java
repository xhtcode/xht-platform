package com.xht.workflow.process.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.workflow.process.enums.FlowTimeLimitTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程时限
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程时限查询")
public class FlowTimeLimitPageQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

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

}
