package com.xht.generate.domain.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.mybatis.extension.handlers.JsonTypeHandler;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.domain.TableExtConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

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
    private String id;

    /**
     * 模板分组id
     */
    @NotNull(message = "模板分组id不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "模板分组id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @NotNull(message = "数据源ID不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据源ID")
    private Long dataSourceId;

    /**
     * 表注释（如：用户表）
     */
    @NotBlank(message = "表注释不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "表注释")
    private String tableComment;

    /**
     * 代码名称（如：User）
     */
    @NotBlank(message = "代码名称不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "代码名称")
    private String codeName;

    /**
     * 代码注释（如：用户）
     */
    @NotBlank(message = "代码注释不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "代码注释")
    private String codeComment;


    /**
     * 配置属性
     */
    @Valid
    @Schema(description = "配置属性")
    private TableExtConfig extConfig;


    @Valid
    @Schema(description = "字段信息")
    private List<GenColumnInfoFormRequest> columns;

}
