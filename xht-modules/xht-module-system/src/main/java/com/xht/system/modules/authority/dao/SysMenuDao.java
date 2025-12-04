package com.xht.system.modules.authority.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.domain.entity.SysMenuEntity;
import com.xht.system.modules.authority.domain.form.SysMenuForm;
import com.xht.system.modules.authority.domain.query.SysMenuQuery;

import java.util.List;

/**
 * 系统菜单管理 Dao
 *
 * @author xht
 **/
public interface SysMenuDao extends MapperRepository<SysMenuEntity> {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    void updateFormRequest(SysMenuForm form);

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     */
    void updateStatus(Long id, MenuStatusEnums status);

    /**
     * 判断菜单ID是否存在
     *
     * @param menuIds 菜单ID列表
     * @return 是否存在
     */
    Boolean existsMenuIds(List<Long> menuIds);

    /**
     * 查询菜单类型
     *
     * @param menuId 菜单ID
     * @return 只返回菜单类型
     */
    MenuTypeEnums getMenuType(Long menuId);

    /**
     * 根据查询条件获取菜单列表
     *
     * @param query 查询请求参数
     * @return LambdaQueryWrapper<SysMenuEntity>
     */
    List<SysMenuEntity> getMenuList(SysMenuQuery query);

    /**
     * 查询可用菜单并构建树形结构
     *
     * @return 菜单树结构
     */
    List<SysMenuEntity> getMenuTreeSystemTool(boolean button);

}
