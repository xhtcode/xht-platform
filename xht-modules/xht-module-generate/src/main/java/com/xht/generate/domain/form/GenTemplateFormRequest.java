package com.xht.generate.domain.form;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * 模板表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "模板表单请求参数")
public class GenTemplateFormRequest extends FormRequest {

    /**
     * id
     */
    @Null(message = "id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "id参数不合法", groups = {Groups.Update.class})
    @Schema(description = "id")
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