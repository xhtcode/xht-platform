package com.xht.modules.router;

import com.xht.api.system.domain.response.SysMenuResponse;
import com.xht.api.system.enums.MenuCommonStatus;
import com.xht.framework.core.exception.UtilException;
import com.xht.modules.router.vo.RouterMeta;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 路由工具类
 *
 * @author xht
 **/
@Slf4j
public final class RouterUtils {

    public RouterUtils() {
        throw new UtilException("Utility class");
    }

    /**
     * 获取菜单元数据
     *
     * @param menu 菜单信息
     * @return 菜单元数据
     */
    public static RouterMeta generateRouter(SysMenuResponse menu) {
        RouterMeta routerMeta = new RouterMeta();
        routerMeta.setTitle(menu.getMenuName());
        routerMeta.setIcon(menu.getMenuIcon());
        routerMeta.setLinkStatus(Objects.requireNonNullElse(menu.getFrameFlag(), MenuCommonStatus.NO).getStatus());
        routerMeta.setMenuType(menu.getMenuType().getValue());
        routerMeta.setAffixStatus(Objects.requireNonNullElse(menu.getAffixStatus(), MenuCommonStatus.NO).getStatus());
        routerMeta.setActiveMenuPath(menu.getActiveMenuPath());
        routerMeta.setHiddenStatus(Objects.requireNonNullElse(menu.getMenuHidden(), MenuCommonStatus.YES).getStatus());
        routerMeta.setKeepAliveStatus(Objects.requireNonNullElse(menu.getMenuCache(), MenuCommonStatus.YES).getStatus());
        routerMeta.setRank(menu.getMenuSort());
        return routerMeta;
    }
}
