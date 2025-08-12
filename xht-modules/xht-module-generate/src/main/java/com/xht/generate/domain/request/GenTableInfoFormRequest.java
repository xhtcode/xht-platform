package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

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
    private Long id;
    
}
