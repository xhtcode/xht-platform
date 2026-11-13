package com.xht.platform.file.domain.form;

import com.xht.platform.file.enums.FileAccessScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class SysFileUsedForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用途编码，创建后不可改")
    private String usedCode;

    @Schema(description = "用途名称")
    private String usedName;

    @Schema(description = "说明")
    private String description;

    @Schema(description = "是否启用")
    private Integer enabled;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "默认访问范围")
    private FileAccessScopeEnum accessScope;

    @Schema(description = "是否允许设为 public")
    private Integer publicAllowed;

    @Schema(description = "下载闸门")
    private Integer downloadAllowed;

    @Schema(description = "打印闸门")
    private Integer printAllowed;

}
