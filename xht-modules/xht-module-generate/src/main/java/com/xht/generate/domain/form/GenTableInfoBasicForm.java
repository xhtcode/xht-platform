package com.xht.generate.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.web.validation.Groups;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.PageStyleEnums;
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
public class GenTableInfoBasicForm extends BasicForm {

    /**
     * Id
     */
    @Null(message = "Id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "Id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "Id参数不合法", groups = { Groups.Update.class})
    @Schema(description = "Id", example = "101")
    private Long id;

    /**
     * 分组id
     */
    @NotNull(message = "分组id不能为空")
    @Schema(description = "分组id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @NotNull(message = "数据源ID不能为空")
    @Schema(description = "数据源ID")
    private Long dataSourceId;

    /**
     * 数据库类型
     */
    @NotNull(message = "数据库类型不能为空")
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dataBaseType;

    /**
     * 引擎名称
     */
    @NotNull(message = "引擎名称不能为空")
    @Schema(description = "引擎名称")
    private String engineName;

    /**
     * 数据库表名
     */
    @NotNull(message = "数据库表名不能为空")
    @Schema(description = "数据库表名")
    private String tableName;

    /**
     * 表注释
     */
    @NotNull(message = "表注释不能为空")
    @Schema(description = "表注释")
    private String tableComment;

    /**
     * 模块名称
     */
    @NotNull(message = "模块名称不能为空")
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 业务名称
     */
    @NotNull(message = "业务名称不能为空")
    @Schema(description = "业务名称")
    private String serviceName;

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
     * 后端作者
     */
    @NotNull(message = "后端作者不能为空")
    @Schema(description = "后端作者")
    private String backEndAuthor;

    /**
     * 前端作者
     */
    @NotNull(message = "前端作者不能为空")
    @Schema(description = "前端作者")
    private String frontEndAuthor;

    /**
     * 请求前缀
     */
    @NotNull(message = "请求前缀不能为空")
    @Schema(description = "请求前缀")
    private String urlPrefix;

    /**
     * 权限前缀
     */
    @NotNull(message = "权限前缀不能为空")
    @Schema(description = "权限前缀")
    private String permissionPrefix;

    /**
     * 上级菜单
     */
    @NotNull(message = "上级菜单不能为空")
    @Schema(description = "上级菜单")
    private Long parentMenuId;

    /**
     * 页面风格
     */
    @NotNull(message = "页面风格不能为空")
    @Schema(description = "页面风格")
    private PageStyleEnums pageStyle;

    /**
     * 页面宽度
     */
    @NotNull(message = "页面宽度不能为空")
    @Schema(description = "页面宽度")
    private Integer pageStyleWidth;

    /**
     * 每行数量
     */
    @NotNull(message = "每行数量不能为空")
    @Schema(description = "每行数量")
    private Integer fromNumber;
}
