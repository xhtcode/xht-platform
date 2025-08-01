package com.xht.system.modules.authority.utils;

import com.xht.framework.core.exception.ValidationException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;

import com.xht.system.modules.authority.common.enums.*;
import com.xht.system.modules.authority.domain.request.SysMenuFormRequest;

import java.util.Objects;

/**
 * 菜单校验格式化工具类
 *
 * @author xht
 **/
public final class MenuValidationFormat {

    private MenuValidationFormat() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 菜单类型校验
     *
     * @param parentType 父菜单类型
     * @param menuType   菜单类型
     */
    public static void checkParentType(MenuTypeEnums parentType, MenuTypeEnums menuType) {
        switch (parentType) {
            case ALL:
                // 可根据实际业务需求添加ALL类型的校验逻辑，这里暂时使用与按钮类型相同的逻辑
                ThrowUtils.throwIf(BusinessErrorCode.PARAM_ERROR, "ALL类型下不能添加子菜单、目录、按钮");
                break;
            case M:
                ThrowUtils.throwIf(Objects.equals(menuType, MenuTypeEnums.B), () -> ex("menuType", "目录类型下不能添加按钮"));
                break;
            case C:
                ThrowUtils.throwIf(Objects.equals(menuType, MenuTypeEnums.M), () -> ex("menuType", "菜单类型下不能添加目录"));
                ThrowUtils.throwIf(Objects.equals(menuType, MenuTypeEnums.C), () -> ex("menuType", "菜单类型下不能添加菜单"));
                break;
            case B:
                ThrowUtils.throwIf(BusinessErrorCode.PARAM_ERROR, "按钮类型下不能添加子菜单、目录、按钮");
                break;
        }
    }

    /**
     * 菜单校验格式化
     *
     * @param formRequest 菜单表单请求参数
     * @throws ValidationException 校验异常
     */
    public static void validationFormat(SysMenuFormRequest formRequest) {
        ThrowUtils.notNull(formRequest, "请求参数不能为空");
        Long parentId = formRequest.getParentId();
        MenuTypeEnums menuType = formRequest.getMenuType();
        String menuName = formRequest.getMenuName();
        String menuIcon = formRequest.getMenuIcon();
        String menuPath = formRequest.getMenuPath();
        MenuHiddenEnums menuHidden = formRequest.getMenuHidden();
        MenuCacheEnums menuCache = formRequest.getMenuCache();
        MenuStatusEnums menuStatus = formRequest.getMenuStatus();
        Integer menuSort = formRequest.getMenuSort();
        String viewName = formRequest.getViewName();
        String viewPath = formRequest.getViewPath();
        String activeMenuPath = formRequest.getActiveMenuPath();
        MenuLinkEnums frameFlag = formRequest.getFrameFlag();
        //公共字段的校验
        ThrowUtils.throwIf(Objects.isNull(parentId), () -> ex("parentId", "父菜单ID不能为空"));
        ThrowUtils.throwIf(Objects.isNull(menuType), () -> ex("menuType", "菜单类型不能为空"));
        ThrowUtils.throwIf(StringUtils.isEmpty(menuName), () -> ex("menuName", "菜单名称不能为空"));
        ThrowUtils.throwIf(Objects.isNull(menuStatus), () -> ex("menuStatus", "菜单状态不能为空"));
        ThrowUtils.throwIf(Objects.isNull(menuSort), () -> ex("menuSort", "菜单排序不能为空"));
        switch (menuType) {
            case M:
                ThrowUtils.throwIf(StringUtils.isEmpty(menuIcon), () -> ex("menuIcon", "目录图标不能为空"));
                ThrowUtils.throwIf(StringUtils.isEmpty(menuPath), () -> ex("menuPath", "目录路由地址不能为空"));
                ThrowUtils.throwIf(StringUtils.isEmpty(menuPath), () -> ex("menuPath", "路由地址不能为空"));
                formRequest.setFrameFlag(null);
                formRequest.setMenuHidden(null);
                formRequest.setMenuCache(null);
                formRequest.setActiveMenuPath(null);
                break;
            case C:
                ThrowUtils.throwIf(StringUtils.isEmpty(menuIcon), () -> ex("menuIcon", "菜单图标不能为空"));
                ThrowUtils.throwIf(StringUtils.isEmpty(menuPath), () -> ex("menuPath", "菜单路由地址不能为空"));
                ThrowUtils.throwIf(StringUtils.isEmpty(menuPath), () -> ex("menuPath", "路由地址不能为空"));
                ThrowUtils.throwIf(Objects.isNull(frameFlag), () -> ex("frameFlag", "是否为外链不能为空"));
                ThrowUtils.throwIf(Objects.isNull(menuHidden), () -> ex("menuHidden", "显示状态不能为空"));
                if (MenuLinkEnums.NO.equals(frameFlag)) {
                    ThrowUtils.throwIf(Objects.isNull(menuCache), () -> ex("menuCache", "是否缓存不能为空"));
                    ThrowUtils.throwIf(StringUtils.isEmpty(viewName), () -> ex("viewName", "组件视图名称不能为空"));
                    ThrowUtils.throwIf(StringUtils.isEmpty(viewPath), () -> ex("viewPath", "组件视图路径不能为空"));
                    formRequest.setMenuCache(null);
                    formRequest.setViewName(null);
                    formRequest.setViewPath(null);
                }
                if (MenuHiddenEnums.HIDE.equals(menuHidden)) {
                    ThrowUtils.throwIf(StringUtils.isEmpty(activeMenuPath), () -> ex("activeMenuPath", "菜单显示不能为空"));
                } else {
                    formRequest.setActiveMenuPath(formRequest.getMenuPath());
                }
                break;
            case B:
                formRequest.setMenuIcon(null);
                formRequest.setMenuPath(null);
                formRequest.setMenuHidden(null);
                formRequest.setMenuCache(null);
                formRequest.setViewName(null);
                formRequest.setViewPath(null);
                formRequest.setActiveMenuPath(null);
                formRequest.setFrameFlag(null);
                break;
            default:
                break;
        }

    }

    private static ValidationException ex(String field, String message) {
        return new ValidationException(field, message);
    }
}
