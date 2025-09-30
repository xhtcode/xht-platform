package com.xht.system.modules.authority.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
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
public class SysRoleResp extends BasicResponse {

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
     * 数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限 5本岗位数据权限  6仅本人数据权限）
     */
    @Schema(description = "数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限 5本岗位数据权限  6仅本人数据权限）")
    private Integer dataScope;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnums roleStatus;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer roleSort;

}
