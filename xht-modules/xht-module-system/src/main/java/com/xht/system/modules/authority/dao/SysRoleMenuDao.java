package com.xht.system.modules.authority.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.mybatis.dao.BasicDao;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.domain.entity.SysRoleMenuEntity;
import com.xht.system.modules.authority.mapper.SysRoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 角色菜单关系Dao
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysRoleMenuDao extends BasicDao<SysRoleMenuMapper, SysRoleMenuEntity> {

    /***
     * 角色菜单绑定
     * @param roleId 角色ID
     * @param roleMenuEntities 角色菜单关系集合
     * @return true/false
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean roleMenuBind(Long roleId, List<SysRoleMenuEntity> roleMenuEntities) {
        LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenuEntity::getRoleId, roleId);
        this.remove(queryWrapper);
        if (CollectionUtils.isEmpty(roleMenuEntities)) {
            return true;
        }
        return saveBatch(roleMenuEntities);
    }

    /**
     * 根据角色ID获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    public List<Long> getRoleId(String roleId) {
        return this.getBaseMapper().selectMenuIdByRoleId(MenuStatusEnums.NORMAL, roleId);
    }
}
