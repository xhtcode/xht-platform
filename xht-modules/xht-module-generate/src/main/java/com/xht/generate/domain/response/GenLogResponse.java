package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.generate.constant.enums.GenerateStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 生成日志响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "生成日志响应信息")
public class GenLogResponse extends BasicResponse {

    /**
     * 历史记录ID
     */
    @Schema(description = "历史记录ID")
    private Long id;

    /**
     * 模板分组id
     */
    @Schema(description = "模板分组id")
    private Long groupId;

    /**
     * 生成批次号
     */
    @Schema(description = "生成批次号")
    private String batchNo;

    /**
     * 生成时间
     */
    @Schema(description = "生成时间")
    private LocalDateTime generateTime;

    /**
     * 生成文件数量
     */
    @Schema(description = "生成文件数量")
    private int fileCount;

    /**
     * 生成的表ID（逗号分隔）
     */
    @Schema(description = "生成的表ID")
    private String tableIds;

    /**
     * 生成状态（success/fail）
     */
    @Schema(description = "生成状态")
    private GenerateStatusEnums status;

    /**
     * 错误信息（失败时记录）
     */
    @Schema(description = "错误信息")
    private String errorMsg;

}
