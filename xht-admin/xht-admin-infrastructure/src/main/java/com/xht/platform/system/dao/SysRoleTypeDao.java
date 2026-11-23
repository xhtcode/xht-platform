package com.xht.platform.system.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.system.domain.form.SysRoleTypeForm;
import com.xht.platform.system.domain.query.SysRoleTypeQuery;
import com.xht.platform.system.entity.SysRoleTypeEntity;
import com.xht.platform.system.enums.RoleStatusEnum;

import java.util.Collection;
import java.util.List;

/**
 * 角色类型管理 Dao
 *
 * @author xht
 **/
public interface SysRoleTypeDao extends MapperRepository<SysRoleTypeEntity> {

    /**
     * 更新角色类型信息
     *
     * @param id   角色类型ID
     * @param form 角色类型信息
     */
    void updateFormRequest(Long id, SysRoleTypeForm form);

    /**
     * 更新角色类型状态
     *
     * @param id     角色类型ID
     * @param status 角色类型状态
     */
    void updateStatus(Long id, RoleStatusEnum status);

    /**
     * 角色类型编码是否存在
     *
     * @param id       角色类型ID
     * @param typeCode 类型编码
     * @return 是否存在
     */
    Boolean existsTypeCode(Long id, String typeCode);

    /**
     * 分页查询角色类型
     *
     * @param page  分页信息
     * @param query 角色类型查询请求参数
     * @return 角色类型分页信息
     */
    Page<SysRoleTypeEntity> findPageList(Page<SysRoleTypeEntity> page, SysRoleTypeQuery query);

    /**
     * 根据角色类型状态查询角色类型列表
     *
     * @param typeStatus 角色类型状态
     * @return 角色类型列表信息
     */
    List<SysRoleTypeEntity> findListByStatus(RoleStatusEnum typeStatus);

    /**
     * 根据角色类型ID集合查询角色类型列表
     *
     * @param ids 角色类型ID集合
     * @return 角色类型列表信息
     */
    List<SysRoleTypeEntity> findListByIds(Collection<Long> ids);

}
