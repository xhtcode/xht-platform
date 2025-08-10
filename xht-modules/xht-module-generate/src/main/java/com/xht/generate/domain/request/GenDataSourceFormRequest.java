package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
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
     * 唯一标识
     */
    @Null(message = "唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "唯一标识参数不合法", groups = { Groups.Update.class})
    @Schema(description = "唯一标识", example = "101")
    private Long id;

}
