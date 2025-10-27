package com.xht.system.modules.authority.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 角色分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "角色分页查询参数")
public class SysRoleBasicQuery extends PageBasicQuery {

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 状态（0正常 1停用）
     */
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnums roleStatus;
}
