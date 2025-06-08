package com.xht.system.authority.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.system.authority.common.enums.RoleStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 角色查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "角色查询参数")
public class SysRoleQueryRequest extends PageQueryRequest {

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
