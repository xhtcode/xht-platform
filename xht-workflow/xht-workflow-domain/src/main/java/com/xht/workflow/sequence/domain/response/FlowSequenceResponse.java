package com.xht.workflow.sequence.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.workflow.sequence.enums.IsCycleEnums;
import com.xht.workflow.sequence.enums.ResetFlagEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 描述：流程序列管理响应
 *
 * @author xht
 **/
@Data
@Schema(description = "流程序列管理响应")
public class FlowSequenceResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序列id
     */
    @Schema(description = "序列id")
    private Long id;

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
     * 序列格式 示例：肥料登记的流水号 032000{YYYYMMDD}-{N} YYYYMMDD,当前日期的格式定义，支持YYYY,YY,MM,DD几种格式组合 {N}原值显示当前值 {N6}当前值显示的最小长度为6位，不足时前面补零
     */
    @Schema(description = "序列格式")
    private String sequenceFormat;

    /**
     * 序列最小值
     */
    @Schema(description = "序列最小值")
    private Integer minValue;

    /**
     * 序列最大值
     */
    @Schema(description = "序列最大值")
    private Integer maxValue;

    /**
     * 序列当前值
     */
    @Schema(description = "序列当前值")
    private Integer currentValue;

    /**
     * 序列步进值
     */
    @Schema(description = "序列步进值")
    private Integer steppingValue;

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
