package com.xht.api.system.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import com.xht.api.system.enums.RoleStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 系统角色表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "系统角色表单请求参数")
public class SysRoleForm extends BasicForm {

    /**
     * 角色唯一标识
     */
    @Null(message = "角色唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "角色唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "角色唯一标识参数不合法", groups = { Groups.Update.class})
    @Schema(description = "角色唯一标识")
    private Long id;

    /**
     * 角色编码
     */
    @NotEmpty(message = "角色编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @NotEmpty(message = "角色描述参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "角色描述")
    private String remark;

    /**
     * 状态（0正常 1停用）
     */
    @NotNull(message = "状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnums roleStatus;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "显示顺序")
    private Integer roleSort;
}
