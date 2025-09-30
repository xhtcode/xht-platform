package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 模板响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "模板响应信息")
public class GenTemplateResp extends BasicResponse {

    /**
     * 模板ID
     */
    @Schema(description = "模板ID")
    private Long id;

    /**
     *分组id
     */
    @Schema(description="分组id")
    private Long groupId;

    /**
     *模板名称
     */
    @Schema(description="模板名称")
    private String templateName;

    /**
     *模板内容
     */
    @Schema(description="模板内容")
    private String templateContent;

    /**
     *文件路径模板
     */
    @Schema(description="文件路径模板")
    private String templateFilePath;

    /**
     *文件名模板
     */
    @Schema(description="文件名模板")
    private String templateFileName;

    /**
     *文件类型
     */
    @Schema(description="文件类型")
    private String templateFileType;


    /**
     * 忽略的字段,逗号分割"
     */
    @Schema(description="忽略的字段,逗号分割")
    private List<String> templateIgnoreField;

    /**
     * 模板排序
     */
    @Schema(description="模板排序")
    private Integer templateSort;
}
