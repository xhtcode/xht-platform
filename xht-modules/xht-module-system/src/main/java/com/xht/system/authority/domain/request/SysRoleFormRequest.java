package com.xht.system.authority.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.authority.common.enums.RoleStatusEnums;
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
public class SysRoleFormRequest extends FormRequest {

    /**
     * 角色唯一标识
     */
    @Null(message = "角色唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "角色唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "角色唯一标识参数不合法", groups = { Groups.Update.class})
    @Schema(description = "角色唯一标识", example = "101")
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
     * 数据范围（1全部数据权限 2自定数据权限 3本角色数据权限 4本角色及以下数据权限 5本岗位数据权限  6仅本人数据权限）
     */
    @NotNull(message = "数据范围参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "数据范围（1全部数据权限 2自定数据权限 3本角色数据权限 4本角色及以下数据权限 5本岗位数据权限  6仅本人数据权限）")
    private Integer dataScope;

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
