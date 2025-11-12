package com.xht.system.modules.authority.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统菜单分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "系统菜单分页查询参数")
public class SysMenuQuery extends PageBasicQuery {
    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID", example = "1")
    private Long parentId;

    /**
     * 类型
     */
    @Schema(description = "菜单类型", example = "1")
    private MenuTypeEnums menuType;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称", example = "系统管理")
    private String menuName;

    /**
     * 菜单状态 （0正常 1停用）
     */
    @Schema(description = "菜单状态 （0正常 1停用）", example = "MenuStatusEnums.ACTIVE")
    private MenuStatusEnums menuStatus;


}
