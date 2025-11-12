package com.xht.generate.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 项目表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "项目表单请求参数")
public class GenTemplateGroupForm extends BasicForm {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = {Groups.Update.class})
    @Schema(description = "Id", example = "101")
    private Long id;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 分组描述
     */
    @Schema(description = "分组描述")
    private Integer groupSort;

    /**
     * 分组描述
     */
    @NotBlank(message = "分组描述不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "分组描述")
    private String groupDesc;
}