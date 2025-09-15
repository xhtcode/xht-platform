package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 模板响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "模板响应信息")
public class GenTemplateResponse extends BasicResponse {

    /**
     * 模板ID
     */
    @Schema(description = "模板ID")
    private Long id;

    /**
     * 模板分组id
     */
    @Schema(description = "模板分组id")
    private Long groupId;

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    private String name;

    /**
     * 模板内容（Velocity语法）
     */
    @Schema(description = "模板内容（Velocity语法）")
    private String content;

    /**
     * 文件路径模板（如：src/main/java/{{package}}）
     */
    @Schema(description = "文件路径模板")
    private String filePathTemplate;

    /**
     * 文件名模板（如：{{className}}Controller.java）
     */
    @Schema(description = "文件名模板")
    private String fileNameTemplate;
}
