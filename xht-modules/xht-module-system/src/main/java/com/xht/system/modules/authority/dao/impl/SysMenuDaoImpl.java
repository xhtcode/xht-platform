package com.xht.system.modules.authority.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.dao.SysMenuDao;
import com.xht.system.modules.authority.dao.mapper.SysMenuMapper;
import com.xht.system.modules.authority.domain.entity.SysMenuEntity;
import com.xht.system.modules.authority.domain.request.SysMenuFormRequest;
import com.xht.system.modules.authority.domain.request.SysMenuQueryRequest;
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
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
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
    @Override
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
    @Override
    public Boolean existsMenuIds(List<Long> menuIds) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenuEntity::getId, menuIds);
        return exists(queryWrapper);
    }

    /**
     * 获取排除特定菜单类型的菜单列表
     *
     * @param excludedType 需要排除的菜单类型
     * @return 菜单列表
     */
    @Override
    public List<SysMenuEntity> getMenusExcludingType(MenuTypeEnums excludedType) {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysMenuEntity::getMenuType, excludedType);
        return list(queryWrapper);
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
     * @param queryRequest 查询请求参数
     * @return LambdaQueryWrapper<SysMenuEntity>
     */
    @Override
    public List<SysMenuEntity> getMenuList(SysMenuQueryRequest queryRequest) {
        // @formatter:off
        LambdaQueryWrapper<SysMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(StringUtils.hasText(queryRequest.getKeyWord()), wrapper ->
                wrapper.like(SysMenuEntity::getMenuName, queryRequest.getKeyWord())
                        .or()
                        .like(SysMenuEntity::getMenuAuthority, queryRequest.getKeyWord())
        );
        lambdaQueryWrapper
                .eq(Objects.nonNull(queryRequest.getParentId()), SysMenuEntity::getParentId, queryRequest.getParentId())
                .eq(Objects.nonNull(queryRequest.getMenuType()), SysMenuEntity::getMenuType, queryRequest.getMenuType())
                .like(StringUtils.hasText(queryRequest.getMenuName()), SysMenuEntity::getMenuName, queryRequest.getMenuName())
                .eq(Objects.nonNull(queryRequest.getMenuStatus()), SysMenuEntity::getMenuStatus, queryRequest.getMenuStatus());
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
