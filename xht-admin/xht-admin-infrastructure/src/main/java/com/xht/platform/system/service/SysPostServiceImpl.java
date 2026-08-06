package com.xht.platform.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.ThrowUtils;
import  com.xht.platform.system.converter.SysPostConverter;
import  com.xht.platform.system.dao.SysPostDao;
import  com.xht.platform.system.domain.form.SysPostForm;
import  com.xht.platform.system.domain.query.SysPostQuery;
import  com.xht.platform.system.domain.response.SysPostResponse;
import  com.xht.platform.system.entity.SysPostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param postId 部门岗位ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long postId) {
        sysPostDao.removeById(postId);
    }

    /**
     * 根据ID更新部门岗位
     *
     * @param postId 部门岗位ID
     * @param form   部门岗位更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Long postId, SysPostForm form) {
        Boolean postCodeExists = sysPostDao.existsPostCode(form.getPostCode(), postId);
        ThrowUtils.throwIf(postCodeExists, BusinessErrorCode.DATA_NOT_EXIST, "岗位编码已存在");
        sysPostDao.updateFormRequest(postId, form);
    }

    /**
     * 根据ID查询部门岗位
     *
     * @param postId 部门岗位ID
     * @return 部门岗位信息
     */
    @Override
    public SysPostResponse findById(Long postId) {
        SysPostEntity sysPostEntity = sysPostDao.findOptionalById(postId).orElse(null);
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