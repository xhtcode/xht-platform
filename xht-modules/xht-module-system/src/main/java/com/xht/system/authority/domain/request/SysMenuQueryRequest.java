package com.xht.system.authority.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.system.authority.common.enums.MenuStatusEnums;
import com.xht.system.authority.common.enums.MenuTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统菜单查询请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "系统菜单查询请求参数")
public class SysMenuQueryRequest extends PageQueryRequest {
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
