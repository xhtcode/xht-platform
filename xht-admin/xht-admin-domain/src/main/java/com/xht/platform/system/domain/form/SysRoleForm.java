package com.xht.platform.system.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import  com.xht.platform.system.enums.RoleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private RoleStatusEnum roleStatus;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "显示顺序")
    private Integer roleSort;

}