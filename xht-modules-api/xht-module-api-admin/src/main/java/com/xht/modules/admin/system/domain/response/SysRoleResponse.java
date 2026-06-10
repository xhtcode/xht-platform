package com.xht.modules.admin.system.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.modules.admin.system.enums.ImportRoleTypeEnums;
import com.xht.modules.admin.system.enums.RoleStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "角色响应信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRoleResponse extends MetaResponse {

    /**
     * 角色ID
     */
    @Schema(name = "角色ID")
    private Long id;

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
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String remark;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnums roleStatus;

    /**
     *创建用户时默认导入的角色
     */
    @Schema(description = "创建用户时默认导入的角色")
    private ImportRoleTypeEnums importRoleType;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer roleSort;

}
