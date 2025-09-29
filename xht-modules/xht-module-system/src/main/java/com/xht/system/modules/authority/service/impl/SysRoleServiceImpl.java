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
import com.xht.system.modules.authority.domain.request.SysRoleFormRequest;
import com.xht.system.modules.authority.domain.request.SysRoleQueryRequest;
import com.xht.system.modules.authority.domain.response.SysRoleResponse;
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
     * @param formRequest 角色表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(SysRoleFormRequest formRequest) {
        Boolean exists = sysRoleDao.existsRoleCode(null, formRequest.getRoleCode());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "角色编码已存在");
        SysRoleEntity entity = sysRoleConverter.toEntity(formRequest);
        entity.setRoleStatus(RoleStatusEnums.NORMAL);
        entity.setDataScope(0);
        return sysRoleDao.saveTransactional(entity);
    }

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeById(Long id) {
        return sysRoleDao.deleteById(id);
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色id
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeByIds(List<Long> ids) {
        ThrowUtils.notNull(ids, BusinessErrorCode.PARAM_ERROR);
        return sysRoleDao.deleteAllById(ids);
    }

    /**
     * 根据ID更新角色
     *
     * @param formRequest 角色更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(SysRoleFormRequest formRequest) {
        Boolean exists = sysRoleDao.existsRoleCode(formRequest.getId(), formRequest.getRoleCode());
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_EXIST, "角色编码已存在");
        Boolean roleExists = sysRoleDao.exists(SysRoleEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        return sysRoleDao.updateFormRequest(formRequest);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return 操作结果
     */
    @Override
    public Boolean updateStatus(Long id, RoleStatusEnums status) {
        Boolean exists = sysRoleDao.exists(SysRoleEntity::getId, id);
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        return sysRoleDao.updateStatus(id, status);
    }

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @Override
    public SysRoleResponse findById(Long id) {
        SysRoleEntity sysRoleEntity = sysRoleDao.findOptionalById(id).orElse(null);
        return sysRoleConverter.toResponse(sysRoleEntity);
    }

    /**
     * 分页查询角色
     *
     * @param queryRequest 角色查询请求参数
     * @return 角色分页信息
     */
    @Override
    public PageResponse<SysRoleResponse> pageList(SysRoleQueryRequest queryRequest) {
        Page<SysRoleEntity> page = sysRoleDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return sysRoleConverter.toResponse(page);
    }

    /***
     * 查询所有角色
     * @return 角色列表
     */
    @Override
    public List<SysRoleResponse> list() {
        return sysRoleConverter.toResponse(sysRoleDao.queryRolesByStatus(RoleStatusEnums.NORMAL));
    }
}




