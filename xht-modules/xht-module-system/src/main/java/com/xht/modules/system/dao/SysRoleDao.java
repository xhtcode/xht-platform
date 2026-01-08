package com.xht.modules.system.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.api.system.enums.RoleStatusEnums;
import com.xht.modules.system.entity.SysRoleEntity;
import com.xht.api.system.domain.form.SysRoleForm;
import com.xht.api.system.domain.query.SysRoleQuery;

import java.util.List;

/**
 * 系统角色管理 Dao
 *
 * @author xht
 **/
public interface SysRoleDao extends MapperRepository<SysRoleEntity> {

    /**
     * 更新角色信息
     *
     * @param form 角色信息
     */
    void updateFormRequest(SysRoleForm form);

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     */
    void updateStatus(Long id, RoleStatusEnums status);

    /**
     * 角色编码是否存在
     *
     * @param roleId   角色ID
     * @param roleCode 角色编码
     * @return 是否存在
     */
    Boolean existsRoleCode(Long roleId, String roleCode);

    /**
     * 分页查询角色
     *
     * @param page  分页信息
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    Page<SysRoleEntity> findPageList(Page<SysRoleEntity> page, SysRoleQuery query);

    /**
     * 根据角色状态查询角色列表
     *
     * @return 角色列表信息
     */
    List<SysRoleEntity> queryRolesByStatus();

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleIds 角色ID列表
     * @return true：存在，false：不存在
     */
    boolean existsByRoleId(List<Long> roleIds);

}
