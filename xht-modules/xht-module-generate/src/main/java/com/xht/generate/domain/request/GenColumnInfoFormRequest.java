package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.ColumnExtConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 字段信息表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "字段信息表单请求参数")
public class GenColumnInfoFormRequest extends FormRequest {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = {Groups.Update.class})
    @Schema(description = "Id", example = "101")
    private Long id;

    /**
     * 字段代码名称
     */
    @Schema(description = "字段代码名称")
    private String codeName;

    /**
     * 字段代码注释
     */
    @Schema(description = "字段代码注释")
    private String codeComment;

    /**
     * 配置属性
     */
    @Schema(description = "配置属性")
    private ColumnExtConfig extConfig;

}
