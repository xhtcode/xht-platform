package com.xht.system.modules.authority.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
import com.xht.system.modules.authority.converter.SysRoleConverter;
import com.xht.system.modules.authority.dao.SysRoleDao;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.request.SysRoleForm;
import com.xht.system.modules.authority.domain.request.SysRoleQuery;
import com.xht.system.modules.authority.domain.response.SysRoleResp;
import com.xht.system.modules.authority.service.ISysRoleService;
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
        entity.setRoleStatus(RoleStatusEnums.NORMAL);
        entity.setDataScope(0);
        sysRoleDao.saveTransactional(entity);
    }

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        sysRoleDao.deleteById(id);
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(List<Long> ids) {
        ThrowUtils.notNull(ids, BusinessErrorCode.PARAM_ERROR);
        sysRoleDao.deleteAllById(ids);
    }

    /**
     * 根据ID更新角色
     *
     * @param form 角色更新请求参数
     */
    @Override
    public void updateById(SysRoleForm form) {
        Boolean exists = sysRoleDao.existsRoleCode(form.getId(), form.getRoleCode());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "角色编码已存在");
        Boolean roleExists = sysRoleDao.exists(SysRoleEntity::getId, form.getId());
        ThrowUtils.throwIf(roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        sysRoleDao.updateFormRequest(form);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     */
    @Override
    public void updateStatus(Long id, RoleStatusEnums status) {
        Boolean exists = sysRoleDao.exists(SysRoleEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        sysRoleDao.updateStatus(id, status);
    }

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @Override
    public SysRoleResp findById(Long id) {
        SysRoleEntity sysRoleEntity = sysRoleDao.findOptionalById(id).orElse(null);
        return sysRoleConverter.toResponse(sysRoleEntity);
    }

    /**
     * 分页查询角色
     *
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    @Override
    public PageResponse<SysRoleResp> pageList(SysRoleQuery query) {
        Page<SysRoleEntity> page = sysRoleDao.queryPageRequest(PageTool.getPage(query), query);
        return sysRoleConverter.toResponse(page);
    }

    /***
     * 查询所有角色
     * @return 角色列表
     */
    @Override
    public List<SysRoleResp> list() {
        return sysRoleConverter.toResponse(sysRoleDao.queryRolesByStatus());
    }
}




