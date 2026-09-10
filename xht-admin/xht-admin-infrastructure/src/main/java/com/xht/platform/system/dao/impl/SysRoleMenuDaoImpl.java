package com.xht.platform.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.utils.CollectionUtils;
import com.xht.platform.system.dao.SysRoleMenuDao;
import com.xht.platform.system.dao.mapper.SysRoleMenuMapper;
import com.xht.platform.system.entity.SysRoleMenuEntity;
import com.xht.platform.system.enums.MenuStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色菜单关系Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysRoleMenuDaoImpl extends MapperRepositoryImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuDao {

    /**
     * 角色菜单绑定
     *
     * @param roleId           角色ID
     * @param roleMenuEntities 角色菜单关系集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleMenuBind(Long roleId, List<SysRoleMenuEntity> roleMenuEntities) {
        LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenuEntity::getRoleId, roleId);
        this.remove(queryWrapper);
        if (CollectionUtils.isEmpty(roleMenuEntities)) {
            return;
        }
        saveAll(roleMenuEntities);
    }

    /**
     * 根据角色ID获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> findMenuIdByRoleId(String roleId) {
        return baseMapper.selectMenuIdByRoleId(MenuStatusEnum.NORMAL, roleId);
    }

    /**
     * 根据角色ID集合获取菜单ID集合
     *
     * @param roleIds 角色ID集合
     * @return 菜单ID集合
     */
    @Override
    public Set<Long> findMenuIdByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        // 分批查询，避免 IN 参数过多导致 SQL 过长、优化器放弃索引
        Set<Long> menuIds = new HashSet<>();
        for (List<Long> batch : CollectionUtils.split(roleIds, IN_QUERY_BATCH_SIZE)) {
            LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysRoleMenuEntity::getMenuId);
            queryWrapper.in(SysRoleMenuEntity::getRoleId, batch);
            list(queryWrapper).forEach(item -> menuIds.add(item.getMenuId()));
        }
        return menuIds;
    }


    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysRoleMenuEntity, ?> getFieldId() {
        return SysRoleMenuEntity::getRoleId;
    }
}
