package com.xht.system.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.system.dept.converter.SysDeptPostConverter;
import com.xht.system.dept.domain.entity.SysDeptEntity;
import com.xht.system.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.dept.domain.request.SysDeptPostFormRequest;
import com.xht.system.dept.domain.request.SysDeptPostQueryRequest;
import com.xht.system.dept.domain.response.SysDeptPostResponse;
import com.xht.system.dept.domain.vo.SysDeptPostSimpleVo;
import com.xht.system.dept.manager.SysDeptManager;
import com.xht.system.dept.manager.SysDeptPostManager;
import com.xht.system.dept.service.ISysDeptPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 部门岗位Service实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDeptPostServiceImpl implements ISysDeptPostService {

    private final SysDeptManager sysDeptManager;

    private final SysDeptPostManager sysDeptPostManager;

    private final SysDeptPostConverter sysDeptPostConverter;

    /**
     * 创建部门岗位
     *
     * @param formRequest 部门岗位表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(SysDeptPostFormRequest formRequest) {
        Boolean deptExists = sysDeptManager.exists(SysDeptEntity::getId, formRequest.getDeptId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        Boolean postCodeExists = sysDeptPostManager.existsPostCode(formRequest.getDeptId(), formRequest.getPostCode(), null);
        ThrowUtils.throwIf(postCodeExists, BusinessErrorCode.DATA_NOT_EXIST, "岗位编码已存在");
        SysDeptPostEntity sysDeptPostEntity = sysDeptPostConverter.toEntity(formRequest);
        return sysDeptPostManager.saveTransactional(sysDeptPostEntity);
    }

    /**
     * 根据ID删除部门岗位
     *
     * @param id 部门岗位ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeById(Long id) {
        Boolean systemFlag = sysDeptPostManager.validateSystemFlag(id, SystemFlagEnums.YES);
        ThrowUtils.throwIf(systemFlag, BusinessErrorCode.DATA_TYPE_ERROR, "系统内置岗位，禁止删除");
        return sysDeptPostManager.removeById(id);
    }

    /**
     * 根据ID数组批量删除部门岗位
     *
     * @param ids 部门岗位ID数组
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeByIds(List<Long> ids) {
        ThrowUtils.notNull(ids, BusinessErrorCode.PARAM_ERROR);
        Boolean systemFlag = sysDeptPostManager.validateSystemFlag(ids, SystemFlagEnums.YES);
        ThrowUtils.throwIf(systemFlag, BusinessErrorCode.DATA_TYPE_ERROR, "选择得到的数据中存在系统内置岗位，禁止删除");
        return sysDeptPostManager.removeByIds(ids);
    }

    /**
     * 根据ID更新部门岗位
     *
     * @param formRequest 部门岗位更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(SysDeptPostFormRequest formRequest) {
        Boolean systemFlag = sysDeptPostManager.validateSystemFlag(formRequest.getId(), SystemFlagEnums.YES);
        ThrowUtils.throwIf(systemFlag, BusinessErrorCode.DATA_TYPE_ERROR, "系统内置岗位禁止修改");
        Boolean deptExists = sysDeptManager.exists(SysDeptEntity::getId, formRequest.getDeptId());
        ThrowUtils.throwIf(!deptExists, BusinessErrorCode.DATA_NOT_EXIST, "部门不存在");
        Boolean postCodeExists = sysDeptPostManager.existsPostCode(formRequest.getDeptId(), formRequest.getPostCode(), formRequest.getId());
        ThrowUtils.throwIf(postCodeExists, BusinessErrorCode.DATA_NOT_EXIST, "岗位编码已存在");
        return sysDeptPostManager.formRequest(formRequest);
    }

    /**
     * 根据ID查询部门岗位
     *
     * @param id 部门岗位ID
     * @return 部门岗位信息
     */
    @Override
    public SysDeptPostResponse getById(Long id) {
        SysDeptPostEntity sysDeptPostEntity = sysDeptPostManager.getOptById(id).orElse(null);
        return sysDeptPostConverter.toResponse(sysDeptPostEntity);
    }

    /**
     * 分页查询部门岗位
     *
     * @param queryRequest 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    @Override
    public PageResponse<SysDeptPostResponse> findPage(SysDeptPostQueryRequest queryRequest) {
        if (Objects.isNull(queryRequest) || Objects.isNull(queryRequest.getDeptId())) {
            return PageTool.empty();
        }
        LambdaQueryWrapper<SysDeptPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        StringUtils.hasText(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysDeptPostEntity::getPostCode, queryRequest.getKeyWord())
                                .or()
                                .like(SysDeptPostEntity::getPostName, queryRequest.getKeyWord())
                )
                .like(StringUtils.hasText(queryRequest.getPostCode()), SysDeptPostEntity::getPostCode, queryRequest.getPostCode())
                .like(StringUtils.hasText(queryRequest.getPostName()), SysDeptPostEntity::getPostName, queryRequest.getPostName())
                .eq(SysDeptPostEntity::getDeptId, queryRequest.getDeptId())
        ;
        // @formatter:on
        Page<SysDeptPostEntity> page = sysDeptPostManager.page(PageTool.getPage(queryRequest), queryWrapper);
        return sysDeptPostConverter.toResponse(page);
    }

    /**
     * 根据部门ID查询部门岗位
     *
     * @param deptId 部门ID
     * @return 部门岗位信息
     */
    @Override
    public List<SysDeptPostSimpleVo> findListByDeptId(String deptId) {
        LambdaQueryWrapper<SysDeptPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysDeptPostEntity::getId, SysDeptPostEntity::getPostCode, SysDeptPostEntity::getPostName, SysDeptPostEntity::getPostStatus);
        queryWrapper.eq(SysDeptPostEntity::getDeptId, deptId);
        List<SysDeptPostEntity> sysDeptPostEntities = sysDeptPostManager.list(queryWrapper);
        return sysDeptPostConverter.toSimpleVo(sysDeptPostEntities);
    }
}




