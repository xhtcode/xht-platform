package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.cache.TypeMappingCache;
import com.xht.generate.converter.GenTypeMappingConverter;
import com.xht.generate.dao.GenTypeMappingDao;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.form.GenTypeMappingForm;
import com.xht.generate.domain.query.GenTypeMappingQuery;
import com.xht.generate.domain.response.GenTypeMappingResponse;
import com.xht.generate.service.IGenTypeMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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

    private final TypeMappingCache typeMappingCache;

    private final GenTypeMappingConverter genTypeMappingConverter;

    /**
     * 创建字段映射
     *
     * @param form 字段映射表单请求参数
     */
    @Override
    public void create(GenTypeMappingForm form) {
        GenTypeMappingEntity entity = genTypeMappingConverter.toEntity(form);
        genTypeMappingDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除字段映射
     *
     * @param id 字段映射ID
     */
    @Override
    public void removeById(Long id) {
        genTypeMappingDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新字段映射
     *
     * @param form 字段映射更新请求参数
     */
    @Override
    public void updateById(GenTypeMappingForm form) {
        Boolean menuExists = genTypeMappingDao.exists(GenTypeMappingEntity::getId, form.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "字段映射不存在");
        genTypeMappingDao.updateFormRequest(form);
    }

    /**
     * 根据ID查询字段映射
     *
     * @param id 字段映射ID
     * @return 字段映射信息
     */
    @Override
    public GenTypeMappingResponse findById(Long id) {
        return genTypeMappingConverter.toResponse(genTypeMappingDao.findById(id));
    }

    /**
     * 分页查询字段映射
     *
     * @param query 字段映射查询请求参数
     * @return 字段映射分页信息
     */
    @Override
    public PageResponse<GenTypeMappingResponse> findPageList(GenTypeMappingQuery query) {
        Page<GenTypeMappingEntity> page = genTypeMappingDao.findPageList(PageTool.getPage(query), query);
        return genTypeMappingConverter.toResponse(page);
    }

    /**
     * 根据数据库类型和目标编程语言类型查询所有的映射关系
     *
     * @param query 字段映射查询请求参数
     * @return 字段映射信息
     */
    @Override
    public List<GenTypeMappingResponse> findAll(GenTypeMappingQuery query) {
        List<GenTypeMappingEntity> typeMappingList = typeMappingCache.getTypeMappingList(query.getDbType());
        return genTypeMappingConverter.toResponse(typeMappingList);
    }


}
