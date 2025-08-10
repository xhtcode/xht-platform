package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.converter.GenTemplateConverter;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenTemplateFormRequest;
import com.xht.generate.domain.request.GenTemplateQueryRequest;
import com.xht.generate.domain.response.GenTemplateResponse;
import com.xht.generate.service.IGenTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 模板管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTemplateServiceImpl implements IGenTemplateService {

    private final GenTemplateDao genTemplateDao;

    private final GenTemplateConverter genTemplateConverter;

    /**
     * 创建模板
     *
     * @param formRequest 模板表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenTemplateFormRequest formRequest) {
        GenTemplateEntity entity = genTemplateConverter.toEntity(formRequest);
        return genTemplateDao.saveTransactional(entity);
    }


    /**
     * 根据ID删除模板
     *
     * @param id 模板ID
     * @return 操作结果
     */
    @Override
    public Boolean removeById(Long id) {
        return genTemplateDao.removeByIdTransactional(id);
    }

    /**
     * 根据ID更新模板
     *
     * @param formRequest 模板更新请求参数
     * @return 操作结果
     */
    @Override
    public Boolean updateById(GenTemplateFormRequest formRequest) {
        Boolean menuExists = genTemplateDao.exists(GenTemplateEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "模板不存在");
        return genTemplateDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询模板
     *
     * @param id 模板ID
     * @return 模板信息
     */
    @Override
    public GenTemplateResponse getById(Long id) {
        return genTemplateConverter.toResponse(genTemplateDao.findById(id));
    }

    /**
     * 分页查询模板
     *
     * @param queryRequest 模板查询请求参数
     * @return 模板分页信息
     */
    @Override
    public PageResponse<GenTemplateResponse> selectPage(GenTemplateQueryRequest queryRequest) {
        Page<GenTemplateEntity> page = genTemplateDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genTemplateConverter.toResponse(page);
    }


}
