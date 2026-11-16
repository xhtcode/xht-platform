package com.xht.platform.file.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件用途配置表单
 *
 * @author xht
 **/
@Data
@Schema(description = "文件用途配置表单")
public class SysFileUsedForm extends BasicForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用途编码
     */
    @NotEmpty(message = "用途编码不能为空")
    @Schema(description = "used_code")
    private String usedCode;

    /**
     * 用途名称
     */
    @NotEmpty(message = "用途名称不能为空")
    @Schema(description = "used_name")
    private String usedName;

    /**
     * 说明
     */
    @NotEmpty(message = "说明不能为空")
    @Schema(description = "used_desc")
    private String usedDesc;

    /**
     * 是否启用：1 是
     */
    @NotEmpty(message = "是否启用不能为空")
    @Schema(description = "used_enabled")
    private Integer usedEnabled;

    /**
     * 排序
     */
    @NotEmpty(message = "排序不能为空")
    @Schema(description = "used_sort")
    private Integer usedSort;

}
