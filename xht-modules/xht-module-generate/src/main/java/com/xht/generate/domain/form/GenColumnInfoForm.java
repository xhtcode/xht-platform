package com.xht.generate.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
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
public class GenColumnInfoForm extends BasicForm {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = {Groups.Update.class})
    @Schema(description = "Id", example = "101")
    private Long id;


    /**
     * 表id
     */
    @NotNull(message = "表id不能为空")
    @Schema(description = "表id")
    private Long tableId;

    /**
     * 表名称(冗余字段)
     */
    @NotNull(message = "表名称不能为空")
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 字段名
     */
    @NotNull(message = "字段名不能为空")
    @Schema(description = "字段名")
    private String dbName;

    /**
     * 字段类型
     */
    @NotNull(message = "字段类型不能为空")
    @Schema(description = "字段类型")
    private String dbType;

    /**
     * 字段主键：0-非主键，1-主键
     */
    @NotNull(message = "字段主键不能为空")
    @Schema(description = "字段主键：0-非主键，1-主键")
    private IdPrimaryKeyEnums dbPrimary;

    /**
     * 字段必填：0-非必填，1-必填
     */
    @NotNull(message = "字段必填不能为空")
    @Schema(description = "字段必填：0-非必填，1-必填")
    private GenStatusEnums dbRequired;

    /**
     * 字段注释
     */
    @NotNull(message = "字段注释不能为空")
    @Schema(description = "字段注释")
    private String dbComment;

    /**
     * 字段长度
     */
    @NotNull(message = "字段长度不能为空")
    @Schema(description = "字段长度")
    private Integer dbLength;

    /**
     * 代码名称
     */
    @NotNull(message = "代码名称不能为空")
    @Schema(description = "代码名称")
    private String codeName;

    /**
     * 代码注释
     */
    @NotNull(message = "代码注释不能为空")
    @Schema(description = "代码注释")
    private String codeComment;

    /**
     * 表单新增：0-不显示，1-显示
     */
    @NotNull(message = "表单新增不能为空")
    @Schema(description = "表单新增")
    private GenStatusEnums fromInsert;

    /**
     * 表单更新：0-不显示，1-显示
     */
    @NotNull(message = "表单更新不能为空")
    @Schema(description = "表单更新")
    private GenStatusEnums fromUpdate;

    /**
     * 表单输入长度
     */
    @NotNull(message = "表单输入长度不能为空")
    @Schema(description = "表单输入长度")
    private Integer fromLength;

    /**
     * 表单必填：0-非必填，1-必填
     */
    @NotNull(message = "表单必填不能为空")
    @Schema(description = "表单必填：0-非必填，1-必填")
    private GenStatusEnums fromFill;

    /**
     * 表单组件
     */
    @NotNull(message = "表单组件不能为空")
    @Schema(description = "表单组件")
    private String fromComponent;

    /**
     * 列表显示：0-不显示，1-显示
     */
    @NotNull(message = "列表显示不能为空")
    @Schema(description = "列表显示：0-不显示，1-显示")
    private GenStatusEnums listShow;

    /**
     * 显示切换禁用：0-不禁用，1-禁用
     */
    @NotNull(message = "显示切换禁用不能为空")
    @Schema(description = "显示切换禁用：0-不禁用，1-禁用")
    private GenStatusEnums listDisabled;

    /**
     * 默认隐藏：0-不隐藏，1-隐藏
     */
    @NotNull(message = "默认隐藏不能为空")
    @Schema(description = "默认隐藏：0-不隐藏，1-隐藏")
    private GenStatusEnums listHidden;

    /**
     * 字段排序
     */
    @NotNull(message = "字段排序不能为空")
    @Schema(description = "字段排序")
    private GenStatusEnums listSortable;

    /**
     * java类型
     */
    @NotNull(message = "java类型不能为空")
    @Schema(description = "java类型")
    private String codeJava;

    /**
     * java类型 包地址
     */
    @NotNull(message = "java类型包地址不能为空")
    @Schema(description = "java类型 包地址")
    private String codeJavaPackage;

    /**
     * ts类型
     */
    @NotNull(message = "ts类型不能为空")
    @Schema(description = "ts类型")
    private String codeTs;

    /**
     * 字典编码
     */
    @Schema(description = "dict_code")
    private String dictCode;

    /**
     * 字段排序
     */
    @NotNull(message = "字段排序不能为空")
    @Schema(description = "字段排序")
    private Integer sortOrder;

}
