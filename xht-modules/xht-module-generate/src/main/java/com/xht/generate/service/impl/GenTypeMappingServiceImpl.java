package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenTypeMappingConverter;
import com.xht.generate.dao.GenTypeMappingDao;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.request.GenTypeMappingFormRequest;
import com.xht.generate.domain.request.GenTypeMappingQueryRequest;
import com.xht.generate.domain.response.GenTypeMappingResponse;
import com.xht.generate.service.IGenTypeMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 字段映射管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTypeMappingServiceImpl implements IGenTypeMappingService {

    private final GenTypeMappingDao genTypeMappingDao;

    private final GenTypeMappingConverter genTypeMappingConverter;

    /**
     * 创建字段映射
     *
     * @param formRequest 字段映射表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenTypeMappingFormRequest formRequest) {
        GenTypeMappingEntity entity = genTypeMappingConverter.toEntity(formRequest);
        return genTypeMappingDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除字段映射
     *
     * @param id 字段映射ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genTypeMappingDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新字段映射
     *
     * @param formRequest 字段映射更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenTypeMappingFormRequest formRequest) {
        Boolean menuExists = genTypeMappingDao.exists(GenTypeMappingEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "字段映射不存在");
        return genTypeMappingDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询字段映射
     *
     * @param id 字段映射ID
     * @return 字段映射信息
     */
    @Override
    public GenTypeMappingResponse getById(Long id) {
        return genTypeMappingConverter.toResponse(genTypeMappingDao.findById(id));
    }

    /**
     * 分页查询字段映射
     *
     * @param queryRequest 字段映射查询请求参数
     * @return 字段映射分页信息
     */
    @Override
    public PageResponse<GenTypeMappingResponse> selectPage(GenTypeMappingQueryRequest queryRequest) {
        Page<GenTypeMappingEntity> page = genTypeMappingDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genTypeMappingConverter.toResponse(page);
    }


}
