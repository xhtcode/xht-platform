package com.xht.platform.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.utils.CollectionUtils;
import com.xht.platform.system.dao.SysRoleTypeMenuDao;
import com.xht.platform.system.dao.mapper.SysRoleTypeMenuMapper;
import com.xht.platform.system.entity.SysRoleTypeMenuEntity;
import com.xht.platform.system.enums.MenuStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色类型菜单关系Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysRoleTypeMenuDaoImpl extends MapperRepositoryImpl<SysRoleTypeMenuMapper, SysRoleTypeMenuEntity> implements SysRoleTypeMenuDao {

    /**
     * 角色类型菜单绑定（先删后插）
     *
     * @param roleTypeId            角色类型ID
     * @param roleTypeMenuEntities  角色类型菜单关系集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleTypeMenuBind(Long roleTypeId, List<SysRoleTypeMenuEntity> roleTypeMenuEntities) {
        LambdaQueryWrapper<SysRoleTypeMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleTypeMenuEntity::getRoleTypeId, roleTypeId);
        this.remove(queryWrapper);
        if (CollectionUtils.isEmpty(roleTypeMenuEntities)) {
            return;
        }
        saveAll(roleTypeMenuEntities);
    }

    /**
     * 根据角色类型ID获取菜单ID集合（仅正常状态且未删除的菜单）
     *
     * @param roleTypeId 角色类型ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> findMenuIdByTypeId(Long roleTypeId) {
        return baseMapper.selectMenuIdByTypeId(MenuStatusEnum.NORMAL, roleTypeId);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysRoleTypeMenuEntity, ?> getFieldId() {
        return SysRoleTypeMenuEntity::getRoleTypeId;
    }
}
