package com.xht.system.modules.authority.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.system.authority.common.enums.*;
import com.xht.system.modules.authority.common.enums.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_menu")
public class SysMenuEntity extends BasicEntity implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 类型
     */
    @TableField(value = "menu_type")
    private MenuTypeEnums menuType;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 菜单图标
     */
    @TableField(value = "menu_icon")
    private String menuIcon;

    /**
     * 路由地址
     */
    @TableField(value = "menu_path")
    private String menuPath;

    /**
     * 显示状态 (0显示 1隐藏)
     */
    @TableField(value = "menu_hidden")
    private MenuHiddenEnums menuHidden;

    /**
     * 是否缓存 （0是 1否）
     */
    @TableField(value = "menu_cache")
    private MenuCacheEnums menuCache;

    /**
     * 菜单状态 （0正常 1停用）
     */
    @TableField(value = "menu_status")
    private MenuStatusEnums menuStatus;

    /**
     * 菜单权限字符串
     */
    @TableField(value = "menu_authority")
    private String menuAuthority;

    /**
     * 菜单排序
     */
    @TableField(value = "menu_sort")
    private Integer menuSort;

    /**
     * 组件视图名称
     */
    @TableField(value = "view_name")
    private String viewName;

    /**
     * 组件视图路径
     */
    @TableField(value = "view_path")
    private String viewPath;

    /**
     * 菜单显示(菜单隐藏时填写)
     */
    @TableField(value = "active_menu_path")
    private String activeMenuPath;

    /**
     * 是否为外链
     */
    @TableField(value = "frame_flag")
    private MenuLinkEnums frameFlag;


}