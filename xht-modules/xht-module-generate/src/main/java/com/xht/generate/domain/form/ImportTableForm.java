package com.xht.generate.domain.form;

import com.xht.framework.core.domain.request.FormRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 导入表请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "导入表请求参数")
public class ImportTableForm extends FormRequest {

    /**
     * 表名称
     */
    @NotEmpty(message = "提交的表名称不能为空")
    @Schema(description = "表名称")
    private List<String> tableNames;

    /**
     * 数据源id
     */
    @NotNull(message = "数据源id不能为空")
    @Schema(description = "数据源id")
    private Long dataSourceId;

    /**
     * 模板分组id
     */
    @NotNull(message = "模板分组id不能为空")
    @Schema(description = "模板分组id")
    private Long groupId;

}
