package com.xht.platform.system.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.platform.system.enums.RoleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色类型分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "角色类型分页查询参数")
public class SysRoleTypeQuery extends PageBasicQuery {

    /**
     * 类型编码
     */
    @Schema(description = "类型编码")
    private String typeCode;

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    private String typeName;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnum typeStatus;

}
