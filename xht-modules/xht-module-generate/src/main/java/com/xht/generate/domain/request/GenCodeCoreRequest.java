package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 代码生成核心请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "代码生成核心请求参数")
public class GenCodeCoreRequest implements IRequest {

    /**
     * 表ID列表
     */
    @NotEmpty(message = "表id不能为空")
    @Schema(description = "表ID列表")
    private List<String> tableIds;

    /**
     * 作者名称
     */
    @NotBlank(message = "作者名称不能为空")
    @Schema(description = "作者名称")
    private String author;

}
