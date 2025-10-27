package com.xht.system.modules.authority.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.dao.SysMenuDao;
import com.xht.system.modules.authority.dao.mapper.SysMenuMapper;
import com.xht.system.modules.authority.domain.entity.SysMenuEntity;
import com.xht.system.modules.authority.domain.form.SysMenuBasicForm;
import com.xht.system.modules.authority.domain.query.SysMenuBasicQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统菜单管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysMenuDaoImpl extends MapperRepositoryImpl<SysMenuMapper, SysMenuEntity> implements SysMenuDao {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(SysMenuBasicForm form) {
        LambdaUpdateWrapper<SysMenuEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(condition(form.getParentId()), SysMenuEntity::getParentId, form.getParentId())
                .set(condition(form.getMenuType()), SysMenuEntity::getMenuType, form.getMenuType())
                .set(condition(form.getMenuStatus()), SysMenuEntity::getMenuStatus, form.getMenuStatus())
                .set(condition(form.getMenuName()), SysMenuEntity::getMenuName, form.getMenuName())
                .set(condition(form.getMenuIcon()), SysMenuEntity::getMenuIcon, form.getMenuIcon())
                .set(condition(form.getMenuPath()), SysMenuEntity::getMenuPath, form.getMenuPath())
                .set(condition(form.getMenuHidden()), SysMenuEntity::getMenuHidden, form.getMenuHidden())
                .set(condition(form.getMenuCache()), SysMenuEntity::getMenuCache, form.getMenuCache())
                .set(condition(form.getMenuAuthority()), SysMenuEntity::getMenuAuthority, form.getMenuAuthority())
                .set(condition(form.getMenuSort()), SysMenuEntity::getMenuSort, form.getMenuSort())
                .set(condition(form.getViewName()), SysMenuEntity::getViewName, form.getViewName())
                .set(condition(form.getViewPath()), SysMenuEntity::getViewPath, form.getViewPath())
                .set(condition(form.getFrameFlag()), SysMenuEntity::getFrameFlag, form.getFrameFlag())
                .eq(SysMenuEntity::getId, form.getId());
        // @formatter:on
        updateWrapper.eq(SysMenuEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, MenuStatusEnums status) {
        LambdaUpdateWrapper<SysMenuEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMenuEntity::getMenuStatus, status);
        updateWrapper.eq(SysMenuEntity::getId, id);
        update(updateWrapper);
    }

    /**
     * 判断菜单ID是否存在
     *
     * @param menuIds 菜单ID列表
     * @return 是否存在
     */
    @Override
    public Boolean existsMenuIds(List<Long> menuIds) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenuEntity::getId, menuIds);
        return exists(queryWrapper);
    }

    /**
     * 查询菜单类型
     *
     * @param menuId 菜单ID
     * @return 只返回菜单类型
     */
    @Override
    public MenuTypeEnums getMenuType(Long menuId) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysMenuEntity::getMenuType);
        queryWrapper.eq(SysMenuEntity::getId, menuId);
        return getOneOpt(queryWrapper).map(SysMenuEntity::getMenuType).orElse(null);
    }

    /**
     * 根据查询条件获取菜单列表
     *
     * @param query 查询请求参数
     * @return LambdaQueryWrapper<SysMenuEntity>
     */
    @Override
    public List<SysMenuEntity> getMenuList(SysMenuBasicQuery query) {
        // @formatter:off
        LambdaQueryWrapper<SysMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(condition(query.getKeyWord()), wrapper ->
                wrapper.like(SysMenuEntity::getMenuName, query.getKeyWord())
                        .or()
                        .like(SysMenuEntity::getMenuAuthority, query.getKeyWord())
        );
        lambdaQueryWrapper
                .eq(condition(query.getParentId()), SysMenuEntity::getParentId, query.getParentId())
                .eq(condition(query.getMenuType()), SysMenuEntity::getMenuType, query.getMenuType())
                .eq(condition(query.getMenuStatus()), SysMenuEntity::getMenuStatus, query.getMenuStatus())
                .like(condition(query.getMenuName()), SysMenuEntity::getMenuName, query.getMenuName());
        // @formatter:on
        return list(lambdaQueryWrapper);
    }

    /**
     * 查询可用菜单并构建树形结构
     *
     * @param menuType 菜单类型过滤条件
     * @return 菜单树结构
     */
    @Override
    public List<SysMenuEntity> listMenuTree(MenuTypeEnums menuType) {
        // @formatter:off
        LambdaQueryWrapper<SysMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(
                SysMenuEntity::getId,
                SysMenuEntity::getParentId,
                SysMenuEntity::getMenuType,
                SysMenuEntity::getMenuName,
                SysMenuEntity::getMenuIcon,
                SysMenuEntity::getMenuSort
        );
        lambdaQueryWrapper
                .ne(!Objects.equals(MenuTypeEnums.ALL, menuType), SysMenuEntity::getMenuType, MenuTypeEnums.B)
                .eq(SysMenuEntity::getMenuStatus, MenuStatusEnums.NORMAL);
        // @formatter:on
        return list(lambdaQueryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysMenuEntity, ?> getFieldId() {
        return SysMenuEntity::getId;
    }
}
