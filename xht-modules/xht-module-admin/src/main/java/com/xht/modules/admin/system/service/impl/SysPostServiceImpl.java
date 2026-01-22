package com.xht.modules.admin.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.modules.admin.system.converter.SysPostConverter;
import com.xht.modules.admin.system.dao.SysPostDao;
import com.xht.modules.admin.system.domain.form.SysPostForm;
import com.xht.modules.admin.system.domain.query.SysPostQuery;
import com.xht.modules.admin.system.domain.response.SysPostResponse;
import com.xht.modules.admin.system.entity.SysPostEntity;
import com.xht.modules.admin.system.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门岗位Service实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl implements ISysPostService {


    private final SysPostDao sysPostDao;

    private final SysPostConverter sysPostConverter;

    /**
     * 创建部门岗位
     *
     * @param form 部门岗位表单请求参数
     */
    @Override
    public void create(SysPostForm form) {
        Boolean postCodeExists = sysPostDao.existsPostCode(form.getPostCode(), null);
        ThrowUtils.throwIf(postCodeExists, BusinessErrorCode.DATA_NOT_EXIST, "岗位编码已存在");
        SysPostEntity sysPostEntity = sysPostConverter.toEntity(form);
        sysPostDao.saveTransactional(sysPostEntity);
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param id 部门岗位ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        Boolean systemFlag = sysPostDao.validateSystemFlag(id, SystemFlagEnums.YES);
        ThrowUtils.throwIf(systemFlag, BusinessErrorCode.DATA_TYPE_ERROR, "系统内置岗位，禁止删除");
        sysPostDao.removeById(id);
    }

    /**
     * 根据ID数组批量删除部门岗位
     *
     * @param ids 部门岗位ID数组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(List<Long> ids) {
        ThrowUtils.notNull(ids, BusinessErrorCode.PARAM_ERROR);
        Boolean systemFlag = sysPostDao.validateSystemFlag(ids, SystemFlagEnums.YES);
        ThrowUtils.throwIf(systemFlag, BusinessErrorCode.DATA_TYPE_ERROR, "选择得到的数据中存在系统内置岗位，禁止删除");
        sysPostDao.removeAllById(ids);
    }

    /**
     * 根据ID更新部门岗位
     *
     * @param form 部门岗位更新请求参数
     */
    @Override
    public void updateById(SysPostForm form) {
        Boolean systemFlag = sysPostDao.validateSystemFlag(form.getId(), SystemFlagEnums.YES);
        ThrowUtils.throwIf(systemFlag, BusinessErrorCode.DATA_TYPE_ERROR, "系统内置岗位禁止修改");
        Boolean postCodeExists = sysPostDao.existsPostCode(form.getPostCode(), form.getId());
        ThrowUtils.throwIf(postCodeExists, BusinessErrorCode.DATA_NOT_EXIST, "岗位编码已存在");
        sysPostDao.updateFormRequest(form);
    }

    /**
     * 根据ID查询部门岗位
     *
     * @param id 部门岗位ID
     * @return 部门岗位信息
     */
    @Override
    public SysPostResponse findById(Long id) {
        SysPostEntity sysPostEntity = sysPostDao.findOptionalById(id).orElse(null);
        return sysPostConverter.toResponse(sysPostEntity);
    }

    /**
     * 分页查询部门岗位
     *
     * @param query 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    @Override
    public PageResponse<SysPostResponse> findPageList(SysPostQuery query) {
        Page<SysPostEntity> page = sysPostDao.findPageList(PageTool.getPage(query), query);
        return sysPostConverter.toResponse(page);
    }

}