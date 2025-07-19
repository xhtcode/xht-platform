package com.xht.system.modules.authority.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.authority.common.enums.*;
import com.xht.system.modules.authority.common.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 系统菜单表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "系统菜单表单请求参数")
public class SysMenuFormRequest extends FormRequest {

    /**
     * 菜单ID唯一标识
     */
    @Null(message = "菜单ID唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "菜单ID唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "菜单ID唯一标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单ID唯一标识", example = "101")
    private Long id;

    /**
     * 父菜单ID
     */
    @NotNull(message = "父菜单ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "父菜单ID", example = "1")
    private Long parentId;

    /**
     * 类型
     */
    @NotNull(groups = {Groups.Create.class, Groups.Update.class}, message = "菜单类型不能为空")
    @Schema(description = "菜单类型", example = "1")
    private MenuTypeEnums menuType;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单名称", example = "系统管理")
    private String menuName;

    /**
     * 菜单图标
     */
    @NotBlank(message = "菜单图标参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单图标", example = "icon-system")
    private String menuIcon;

    /**
     * 路由地址
     */
    @NotBlank(message = "路由地址参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "路由地址", example = "/system")
    private String menuPath;

    /**
     * 显示状态 (0显示 1隐藏)
     */
    @NotNull(message = "显示状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "显示状态 (0显示 1隐藏)", example = "0")
    private MenuHiddenEnums menuHidden;

    /**
     * 是否缓存 （0是 1否）
     */
    @NotNull(message = "是否缓存参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "是否缓存 （0是 1否）", example = "0")
    private MenuCacheEnums menuCache;

    /**
     * 菜单状态 （0正常 1停用）
     */
    @NotNull(message = "菜单状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单状态 （0正常 1停用）", example = "MenuStatusEnums.ACTIVE")
    private MenuStatusEnums menuStatus;

    /**
     * 菜单权限字符串
     */
    @NotBlank(message = "菜单权限字符串参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单权限字符串", example = "system:manage")
    private String menuAuthority;

    /**
     * 菜单排序
     */
    @NotNull(message = "菜单排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "菜单排序必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单排序", example = "1")
    private Integer menuSort;

    /**
     * 组件视图名称
     */
    @NotBlank(message = "组件视图名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "组件视图名称", example = "SystemManageView")
    private String viewName;

    /**
     * 组件视图路径
     */
    @NotBlank(message = "组件视图路径参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "组件视图路径", example = "/views/system/manage")
    private String viewPath;

    /**
     * 菜单显示(菜单隐藏时填写)
     */
    @NotBlank(message = "菜单显示不能超过255个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "菜单显示(菜单隐藏时填写)")
    private String activeMenuPath;

    /**
     * 是否为外链
     */
    @NotNull(message = "是否为外链参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "是否为外链", example = "0")
    private MenuLinkEnums frameFlag;


}
