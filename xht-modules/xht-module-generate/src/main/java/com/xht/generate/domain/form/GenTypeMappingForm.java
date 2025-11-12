package com.xht.generate.domain.form;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Id", example = "101")
    private Long id;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @NotNull(message = "数据库类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @TableField(value = "db_type")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    @NotNull(message = "数据库数据类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @TableField(value = "db_data_type")
    private String dbDataType;

    /**
     * 目标编程语言（Java/TypeScript）
     */
    @NotNull(message = "目标编程语言不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @TableField(value = "target_language")
    private LanguageTypeEnums targetLanguage;

    /**
     * 目标语言数据类型（如：Integer/number）
     */
    @NotNull(message = "目标语言数据类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @TableField(value = "target_data_type")
    private String targetDataType;

    /**
     * 导入包路径（如：java.time.LocalDateTime）
     */
    @TableField(value = "import_package")
    private String importPackage;

}
