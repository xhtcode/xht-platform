package com.xht.modules.system.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.modules.common.enums.MenuCommonStatus;
import com.xht.modules.common.enums.MenuStatusEnums;
import com.xht.modules.common.enums.MenuTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统菜单响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "系统菜单响应信息")
public class SysMenuResponse extends MetaResponse {
    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long id;

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
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String menuIcon;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String menuPath;

    /**
     * 显示状态 (0显示 1隐藏)
     */
    @Schema(description = "显示状态 (0显示 1隐藏)")
    private MenuCommonStatus menuHidden;

    /**
     * 是否缓存 （0是 1否）
     */
    @Schema(description = "是否缓存 （0是 1否）")
    private MenuCommonStatus menuCache;

    /**
     * 菜单状态 （0正常 1停用）
     */
    @Schema(description = "菜单状态 （0正常 1停用）")
    private MenuStatusEnums menuStatus;

    /**
     * 菜单权限字符串
     */
    @Schema(description = "菜单权限字符串")
    private String menuAuthority;

    /**
     * 菜单排序
     */
    @Schema(description = "菜单排序")
    private Integer menuSort;

    /**
     * 组件视图名称
     */
    @Schema(description = "组件视图名称")
    private String viewName;

    /**
     * 组件视图路径
     */
    @Schema(description = "组件视图路径")
    private String viewPath;

    /**
     * 菜单显示(菜单隐藏时填写)
     */
    @Schema(description = "菜单显示(菜单隐藏时填写)")
    private String activeMenuPath;

    /**
     * 固定状态
     */
    @Schema(description = "固定状态")
    private MenuCommonStatus affixStatus;

    /**
     * 是否为外链
     */
    @Schema(description = "是否为外链")
    private MenuCommonStatus frameFlag;

}
