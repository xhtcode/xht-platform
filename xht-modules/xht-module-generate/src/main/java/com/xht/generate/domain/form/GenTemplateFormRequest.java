package com.xht.generate.domain.form;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

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
     * 模板分组id
     */
    @NotNull(message = "模板分组id不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "模板分组id")
    private Long groupId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "模板名称")
    private String name;

    /**
     * 模板内容（Velocity语法）
     */
    @NotBlank(message = "模板内容不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "模板内容（Velocity语法）")
    private String content;


    /**
     * 文件路径模板（如：src/main/java/{{package}}）
     */
    @NotBlank(message = "文件路径模板不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "文件路径模板（如：src/main/java/{{package}}）")
    private String filePathTemplate;

    /**
     * 文件名模板（如：{{className}}Controller.java）
     */
    @NotBlank(message = "文件名模板不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "文件名模板（如：{{className}}Controller.java）")
    private String fileNameTemplate;

}