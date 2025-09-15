package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 数据源表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "数据源表单请求参数")
public class GenDataSourceFormRequest extends FormRequest {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = {Groups.Update.class})
    @Schema(description = "Id", example = "101")
    private Long id;

    /**
     * 数据源名称
     */
    @NotBlank(message = "数据源名称不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据源名称")
    private String name;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @NotNull(message = "数据库类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库地址
     */
    @NotBlank(message = "数据库地址不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据库地址")
    private String url;

}