package com.xht.modules.admin.router;

import com.xht.modules.admin.system.domain.response.SysMenuResponse;
import com.xht.modules.admin.system.enums.MenuCommonStatus;
import com.xht.framework.core.exception.UtilException;
import com.xht.modules.admin.router.dto.RouterMetaDTO;
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
    public static RouterMetaDTO generateRouter(SysMenuResponse menu) {
        RouterMetaDTO metaDTO = new RouterMetaDTO();
        metaDTO.setTitle(menu.getMenuName());
        metaDTO.setIcon(menu.getMenuIcon());
        metaDTO.setLinkStatus(Objects.requireNonNullElse(menu.getFrameFlag(), MenuCommonStatus.NO).getStatus());
        metaDTO.setMenuType(menu.getMenuType().getValue());
        metaDTO.setAffixStatus(Objects.requireNonNullElse(menu.getAffixStatus(), MenuCommonStatus.NO).getStatus());
        metaDTO.setActiveMenuPath(menu.getActiveMenuPath());
        metaDTO.setHiddenStatus(Objects.requireNonNullElse(menu.getMenuHidden(), MenuCommonStatus.YES).getStatus());
        metaDTO.setKeepAliveStatus(Objects.requireNonNullElse(menu.getMenuCache(), MenuCommonStatus.YES).getStatus());
        metaDTO.setRank(menu.getMenuSort());
        return metaDTO;
    }
}
