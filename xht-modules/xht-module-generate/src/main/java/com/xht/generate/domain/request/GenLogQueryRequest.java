package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 生成日志分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "生成日志分页查询参数")
public class GenLogQueryRequest extends PageQueryRequest {

    /**
     * 分组id
     */
    @Schema(description = "分组id")
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
    private LocalDateTime generateTimeStart;

    /**
     * 生成时间
     */
    @Schema(description = "生成时间")
    private LocalDateTime generateTimeEnd;
}
