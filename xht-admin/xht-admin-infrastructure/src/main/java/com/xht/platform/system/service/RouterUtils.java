package com.xht.platform.system.service;

import com.xht.platform.common.router.dto.RouterMetaDTO;
import com.xht.platform.system.domain.response.SysMenuResponse;
import com.xht.platform.system.enums.MenuCommonStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 路由工具类
 *
 * @author xht
 **/
@Slf4j
public abstract class RouterUtils {

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
        metaDTO.setLinkStatus(Objects.requireNonNullElse(menu.getFrameFlag(), MenuCommonStatusEnum.NO).getStatus());
        metaDTO.setMenuType(menu.getMenuType().getValue());
        metaDTO.setAffixStatus(Objects.requireNonNullElse(menu.getAffixStatus(), MenuCommonStatusEnum.NO).getStatus());
        metaDTO.setActiveMenuPath(menu.getActiveMenuPath());
        metaDTO.setHiddenStatus(Objects.requireNonNullElse(menu.getMenuHidden(), MenuCommonStatusEnum.YES).getStatus());
        metaDTO.setKeepAliveStatus(Objects.requireNonNullElse(menu.getMenuCache(), MenuCommonStatusEnum.YES).getStatus());
        metaDTO.setRank(menu.getMenuSort());
        return metaDTO;
    }
}
