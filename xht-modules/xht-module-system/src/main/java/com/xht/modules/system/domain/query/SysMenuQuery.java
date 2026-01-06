package com.xht.modules.system.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.modules.common.enums.MenuStatusEnums;
import com.xht.modules.common.enums.MenuTypeEnums;
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
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 类型
     */
    @Schema(description = "菜单类型")
    private MenuTypeEnums menuType;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 菜单状态 （0正常 1停用）
     */
    @Schema(description = "菜单状态 （0正常 1停用）")
    private MenuStatusEnums menuStatus;


}
