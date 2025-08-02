package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenProjectDataSourceConverter;
import com.xht.generate.dao.GenProjectDataSourceDao;
import com.xht.generate.domain.entity.GenProjectDataSourceEntity;
import com.xht.generate.domain.request.GenProjectDataSourceFormRequest;
import com.xht.generate.domain.request.GenProjectDataSourceQueryRequest;
import com.xht.generate.domain.response.GenProjectDataSourceResponse;
import com.xht.generate.service.IGenProjectDataSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 项目数据源管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenProjectDataSourceServiceImpl implements IGenProjectDataSourceService {

    private final GenProjectDataSourceDao genProjectDataSourceDao;

    private final GenProjectDataSourceConverter genProjectDataSourceConverter;

    /**
     * 创建项目数据源
     *
     * @param formRequest 项目数据源表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenProjectDataSourceFormRequest formRequest) {
        GenProjectDataSourceEntity entity = genProjectDataSourceConverter.toEntity(formRequest);
        return genProjectDataSourceDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除项目数据源
     *
     * @param id 项目数据源ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genProjectDataSourceDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新项目数据源
     *
     * @param formRequest 项目数据源更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenProjectDataSourceFormRequest formRequest) {
        Boolean menuExists = genProjectDataSourceDao.exists(GenProjectDataSourceEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "项目数据源不存在");
        return genProjectDataSourceDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询项目数据源
     *
     * @param id 项目数据源ID
     * @return 项目数据源信息
     */
    @Override
    public GenProjectDataSourceResponse getById(Long id) {
        return genProjectDataSourceConverter.toResponse(genProjectDataSourceDao.findById(id));
    }

    /**
     * 分页查询项目数据源
     *
     * @param queryRequest 项目数据源查询请求参数
     * @return 项目数据源分页信息
     */
    @Override
    public PageResponse<GenProjectDataSourceResponse> selectPage(GenProjectDataSourceQueryRequest queryRequest) {
        Page<GenProjectDataSourceEntity> page = genProjectDataSourceDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genProjectDataSourceConverter.toResponse(page);
    }


                        }
