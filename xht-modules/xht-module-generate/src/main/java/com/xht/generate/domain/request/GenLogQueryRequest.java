package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime generateTimeStart;

    /**
     * 生成时间
     */
    @Schema(description = "生成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime generateTimeEnd;
}
