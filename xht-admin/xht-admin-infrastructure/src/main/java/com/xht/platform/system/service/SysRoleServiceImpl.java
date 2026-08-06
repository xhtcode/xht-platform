package com.xht.platform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.ThrowUtils;
import  com.xht.platform.system.converter.SysRoleConverter;
import  com.xht.platform.system.dao.SysRoleDao;
import  com.xht.platform.system.dao.SysUserRoleDao;
import  com.xht.platform.system.domain.form.SysRoleForm;
import  com.xht.platform.system.domain.query.SysRoleQuery;
import  com.xht.platform.system.domain.response.SysRoleResponse;
import  com.xht.platform.system.entity.SysRoleEntity;
import com.xht.platform.system.entity.SysUserRoleEntity;
import  com.xht.platform.system.enums.RoleStatusEnum;
import com.xht.platform.system.enums.RoleTypeEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色service实现类
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements ISysRoleService {

    private final SysRoleDao sysRoleDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleConverter sysRoleConverter;

    /**
     * 创建角色
     *
     * @param form 角色表单请求参数
     */
    @Override
    public void create(SysRoleForm form) {
        Boolean exists = sysRoleDao.existsRoleCode(null, form.getRoleCode());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "角色编码已存在");
        SysRoleEntity entity = sysRoleConverter.toEntity(form);
        entity.setImportRoleType(RoleTypeEnums.NONE);
        sysRoleDao.saveTransactional(entity);
    }

    /**
     * 批量删除角色
     *
     * @param roleId 角色id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(Long roleId) {
        Boolean existsInId = sysUserRoleDao.exists(SysUserRoleEntity::getRoleId, roleId);
        ThrowUtils.throwIf(existsInId, BusinessErrorCode.DATA_EXIST, "角色已分配用户，禁止删除");
        sysRoleDao.removeById(roleId);
    }

    /**
     * 根据ID更新角色
     *
     * @param roleId 角色ID
     * @param form   角色更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long roleId, SysRoleForm form) {
        Boolean exists = sysRoleDao.existsRoleCode(roleId, form.getRoleCode());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "角色编码已存在");
        Boolean roleExists = sysRoleDao.exists(SysRoleEntity::getId, roleId);
        ThrowUtils.throwIf(!roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        sysRoleDao.updateFormRequest(roleId, form);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     */
    @Override
    public void updateStatus(Long id, RoleStatusEnum status) {
        Boolean exists = sysRoleDao.exists(SysRoleEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        sysRoleDao.updateStatus(id, status);
    }

    /**
     * 根据ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    @Override
    public SysRoleResponse findById(Long roleId) {
        SysRoleEntity sysRoleEntity = sysRoleDao.findOptionalById(roleId).orElse(null);
        return sysRoleConverter.toResponse(sysRoleEntity);
    }

    /**
     * 分页查询角色
     *
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    @Override
    public PageResponse<SysRoleResponse> findPageList(SysRoleQuery query) {
        Page<SysRoleEntity> page = sysRoleDao.findPageList(PageTool.getPage(query), query);
        return sysRoleConverter.toResponse(page);
    }

    /**
     * 查询所有角色
     * @return 角色列表
     */
    @Override
    public List<SysRoleResponse> list() {
        return sysRoleConverter.toResponse(sysRoleDao.queryRolesByStatus());
    }

}




