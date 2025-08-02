package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenDataSourceConverter;
import com.xht.generate.dao.GenDataSourceDao;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.request.GenDataSourceFormRequest;
import com.xht.generate.domain.request.GenDataSourceQueryRequest;
import com.xht.generate.domain.response.GenDataSourceResponse;
import com.xht.generate.service.IGenDataSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 数据源管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenDataSourceServiceImpl implements IGenDataSourceService {

    private final GenDataSourceDao genDataSourceDao;

    private final GenDataSourceConverter genDataSourceConverter;

    /**
     * 创建数据源
     *
     * @param formRequest 数据源表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenDataSourceFormRequest formRequest) {
        GenDataSourceEntity entity = genDataSourceConverter.toEntity(formRequest);
        return genDataSourceDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除数据源
     *
     * @param id 数据源ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genDataSourceDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新数据源
     *
     * @param formRequest 数据源更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenDataSourceFormRequest formRequest) {
        Boolean menuExists = genDataSourceDao.exists(GenDataSourceEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "数据源不存在");
        return genDataSourceDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询数据源
     *
     * @param id 数据源ID
     * @return 数据源信息
     */
    @Override
    public GenDataSourceResponse getById(Long id) {
        return genDataSourceConverter.toResponse(genDataSourceDao.findById(id));
    }

    /**
     * 分页查询数据源
     *
     * @param queryRequest 数据源查询请求参数
     * @return 数据源分页信息
     */
    @Override
    public PageResponse<GenDataSourceResponse> selectPage(GenDataSourceQueryRequest queryRequest) {
        Page<GenDataSourceEntity> page = genDataSourceDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genDataSourceConverter.toResponse(page);
    }


                        }
