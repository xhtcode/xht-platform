package com.xht.generate.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 字段映射表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "字段映射表单请求参数")
public class GenTypeMappingForm extends BasicForm {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = {Groups.Update.class})
    @Schema(description = "Id")
    private Long id;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @NotNull(message = "数据库类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    @NotNull(message = "数据库数据类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据库数据类型")
    private String dbDataType;


    /**
     * java类型
     */
    @NotEmpty(message = "java类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "java类型")
    private String javaType;

    /**
     * java包类型
     */
    @NotEmpty(message = "java包类型不能为空")
    @Schema(description = "java包类型")
    private String importPackage;

    /**
     * ts类型
     */
    @NotEmpty(message = "ts类型型不能为空")
    @Schema(description = "ts类型")
    private String tsType;

}
