package com.xht.system.modules.authority.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.dao.SysRoleMenuDao;
import com.xht.system.modules.authority.dao.mapper.SysRoleMenuMapper;
import com.xht.system.modules.authority.domain.entity.SysRoleMenuEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
     * @param roleId 角色ID
     * @param roleMenuEntities 角色菜单关系集合
     * @return true/false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean roleMenuBind(Long roleId, List<SysRoleMenuEntity> roleMenuEntities) {
        LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenuEntity::getRoleId, roleId);
        this.remove(queryWrapper);
        if (CollectionUtils.isEmpty(roleMenuEntities)) {
            return true;
        }
        return saveAll(roleMenuEntities);
    }

    /**
     * 根据角色ID获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> getRoleId(String roleId) {
        return baseMapper.selectMenuIdByRoleId(MenuStatusEnums.NORMAL, roleId);
    }

    /**
     * 用户id获取菜单集合
     *
     * @param userId 用户id
     * @return 菜单集合
     */
    @Override
    public List<String> findPermissionCodeByUserId(Long userId) {
        return baseMapper.findPermissionCodeByUserId(userId);
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
