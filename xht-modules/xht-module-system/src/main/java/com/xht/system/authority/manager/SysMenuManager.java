package com.xht.system.authority.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.mybatis.manager.BasicManager;
import com.xht.system.authority.common.enums.MenuStatusEnums;
import com.xht.system.authority.domain.entity.SysMenuEntity;
import com.xht.system.authority.domain.request.SysMenuFormRequest;
import com.xht.system.authority.mapper.SysMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统菜单管理
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysMenuManager extends BasicManager<SysMenuMapper, SysMenuEntity> {
    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(SysMenuFormRequest formRequest) {
        LambdaUpdateWrapper<SysMenuEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysMenuEntity::getParentId, formRequest.getParentId())
                .set(SysMenuEntity::getMenuType, formRequest.getMenuType())
                .set(SysMenuEntity::getMenuStatus, formRequest.getMenuStatus())
                .set(SysMenuEntity::getMenuName, formRequest.getMenuName())
                .set(SysMenuEntity::getMenuIcon, formRequest.getMenuIcon())
                .set(SysMenuEntity::getMenuPath, formRequest.getMenuPath())
                .set(SysMenuEntity::getMenuHidden, formRequest.getMenuHidden())
                .set(SysMenuEntity::getMenuCache, formRequest.getMenuCache())
                .set(SysMenuEntity::getMenuAuthority, formRequest.getMenuAuthority())
                .set(SysMenuEntity::getMenuSort, formRequest.getMenuSort())
                .set(SysMenuEntity::getViewName, formRequest.getViewName())
                .set(SysMenuEntity::getViewPath, formRequest.getViewPath())
                .set(SysMenuEntity::getFrameFlag, formRequest.getFrameFlag())
                .eq(SysMenuEntity::getId, formRequest.getId());
        // @formatter:on
        updateWrapper.eq(SysMenuEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Long id, MenuStatusEnums status) {
        LambdaUpdateWrapper<SysMenuEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMenuEntity::getMenuStatus, status);
        updateWrapper.eq(SysMenuEntity::getId, id);
        return update(updateWrapper);
    }

    /**
     * 判断菜单ID是否存在
     *
     * @param menuIds 菜单ID列表
     * @return 是否存在
     */
    public Boolean existsMenuIds(List<Long> menuIds) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenuEntity::getId, menuIds);
        return exists(queryWrapper);
    }
}
