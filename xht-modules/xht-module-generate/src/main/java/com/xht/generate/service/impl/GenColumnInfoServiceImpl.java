package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenColumnInfoConverter;
import com.xht.generate.dao.GenColumnInfoDao;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;
import com.xht.generate.domain.request.GenColumnInfoQueryRequest;
import com.xht.generate.domain.response.GenColumnInfoResponse;
import com.xht.generate.service.IGenColumnInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 字段信息管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenColumnInfoServiceImpl implements IGenColumnInfoService {

    private final GenColumnInfoDao genColumnInfoDao;

    private final GenColumnInfoConverter genColumnInfoConverter;

    /**
     * 创建字段信息
     *
     * @param formRequest 字段信息表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenColumnInfoFormRequest formRequest) {
        GenColumnInfoEntity entity = genColumnInfoConverter.toEntity(formRequest);
        return genColumnInfoDao.saveTransactional(entity);
    }

    /**
     * 根据ID更新字段信息
     *
     * @param formRequest 字段信息更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenColumnInfoFormRequest formRequest) {
        Boolean menuExists = genColumnInfoDao.exists(GenColumnInfoEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "字段信息不存在");
        return genColumnInfoDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询字段信息
     *
     * @param id 字段信息ID
     * @return 字段信息信息
     */
    @Override
    public GenColumnInfoResponse getById(Long id) {
        return genColumnInfoConverter.toResponse(genColumnInfoDao.findById(id));
    }

    /**
     * 分页查询字段信息
     *
     * @param queryRequest 字段信息查询请求参数
     * @return 字段信息分页信息
     */
    @Override
    public PageResponse<GenColumnInfoResponse> selectPage(GenColumnInfoQueryRequest queryRequest) {
        Page<GenColumnInfoEntity> page = genColumnInfoDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genColumnInfoConverter.toResponse(page);
    }


                        }
