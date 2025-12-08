package com.xht.generate.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 代码生成核心请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "代码生成核心请求参数")
public class GenCodeCoreForm extends BasicForm {

    /**
     * 表ID列表
     */
    @NotEmpty(message = "表id不能为空")
    @Schema(description = "表ID列表")
    private List<Long> tableIds;

    /**
     * 包名称
     */
    @NotBlank(message = "包名称不能为空")
    @Schema(description = "包名称")
    private String packageName;

}
