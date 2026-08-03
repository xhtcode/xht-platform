package com.xht.workflow.sequence.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.workflow.sequence.enums.IsCycleEnums;
import com.xht.workflow.sequence.enums.ResetFlagEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 描述：流程序列管理分页查询
 *
 * @author xht
 **/
@Data
@Schema(description = "流程序列管理分页查询")
public class FlowSequencePageQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序列编码
     */
    @Schema(description = "序列编码")
    private String sequenceCode;

    /**
     * 序列名称
     */
    @Schema(description = "序列名称")
    private String sequenceName;

    /**
     * 是否循环
     */
    @Schema(description = "是否循环")
    private IsCycleEnums isCycle;

    /**
     * 重置周期 0 不重置 1每天 2月 3年
     */
    @Schema(description = "重置周期 0 不重置 1每天 2月 3年")
    private ResetFlagEnums resetFlag;

}
