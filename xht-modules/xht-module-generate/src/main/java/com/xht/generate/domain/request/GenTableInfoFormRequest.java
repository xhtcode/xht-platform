package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

/**
 * 表信息表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "表信息表单请求参数")
public class GenTableInfoFormRequest extends FormRequest {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = { Groups.Update.class})
    @Schema(description = "Id", example = "101")
    private String id;

    /**
     * 分组id
     */
    @NotNull(message = "分组id不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "分组id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @NotNull(message = "数据源ID不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据源ID")
    private Long dataSourceId;

    /**
     * 表注释（如：用户表）
     */
    @NotBlank(message = "表注释不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "表注释")
    private String tableComment;

    /**
     * 生成的类名（如：User）
     */
    @NotBlank(message = "生成的类名不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "生成的类名")
    private String codeName;

    /**
     * 代码的注释（如：用户）
     */
    @NotBlank(message = "代码的注释不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "代码的注释")
    private String codeComment;

    @Valid
    @Schema(description = "字段信息")
    private List<GenColumnInfoFormRequest> columns;

}
