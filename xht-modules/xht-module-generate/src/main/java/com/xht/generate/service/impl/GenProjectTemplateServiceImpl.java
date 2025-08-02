package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenProjectTemplateConverter;
import com.xht.generate.dao.GenProjectTemplateDao;
import com.xht.generate.domain.entity.GenProjectTemplateEntity;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.domain.request.GenProjectTemplateQueryRequest;
import com.xht.generate.domain.response.GenProjectTemplateResponse;
import com.xht.generate.service.IGenProjectTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 项目模板管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenProjectTemplateServiceImpl implements IGenProjectTemplateService {

    private final GenProjectTemplateDao genProjectTemplateDao;

    private final GenProjectTemplateConverter genProjectTemplateConverter;

    /**
     * 创建项目模板
     *
     * @param formRequest 项目模板表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenProjectTemplateFormRequest formRequest) {
        GenProjectTemplateEntity entity = genProjectTemplateConverter.toEntity(formRequest);
        return genProjectTemplateDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除项目模板
     *
     * @param id 项目模板ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genProjectTemplateDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新项目模板
     *
     * @param formRequest 项目模板更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenProjectTemplateFormRequest formRequest) {
        Boolean menuExists = genProjectTemplateDao.exists(GenProjectTemplateEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "项目模板不存在");
        return genProjectTemplateDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询项目模板
     *
     * @param id 项目模板ID
     * @return 项目模板信息
     */
    @Override
    public GenProjectTemplateResponse getById(Long id) {
        return genProjectTemplateConverter.toResponse(genProjectTemplateDao.findById(id));
    }

    /**
     * 分页查询项目模板
     *
     * @param queryRequest 项目模板查询请求参数
     * @return 项目模板分页信息
     */
    @Override
    public PageResponse<GenProjectTemplateResponse> selectPage(GenProjectTemplateQueryRequest queryRequest) {
        Page<GenProjectTemplateEntity> page = genProjectTemplateDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genProjectTemplateConverter.toResponse(page);
    }


                        }
