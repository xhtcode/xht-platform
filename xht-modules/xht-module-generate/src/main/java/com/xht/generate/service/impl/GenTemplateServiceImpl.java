package com.xht.generate.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.generate.converter.GenTemplateConverter;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.dao.GenTemplateGroupDao;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.form.GenTemplateFormRequest;
import com.xht.generate.domain.response.GenTemplateResponse;
import com.xht.generate.service.IGenTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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

    private final GenTemplateGroupDao genTemplateGroupDao;

    /**
     * 创建模板
     *
     * @param formRequest 模板表单请求参数
     * @return 操作结果
     */
    @Override
    public Boolean create(GenTemplateFormRequest formRequest) {
        Boolean exists = genTemplateGroupDao.exists(GenTemplateGroupEntity::getId, formRequest.getGroupId());
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "模板组不存在");
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
        Boolean exists = genTemplateGroupDao.exists(GenTemplateGroupEntity::getId, formRequest.getGroupId());
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "模板组不存在");
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
    public GenTemplateResponse findById(Long id) {
        return genTemplateConverter.toResponse(genTemplateDao.findById(id));
    }

    /**
     * 根据模板组ID获取模板列表
     *
     * @param groupId 模板组ID
     * @return 模板响应列表
     */
    @Override
    public List<GenTemplateResponse> listByGroupId(String groupId) {
        return genTemplateConverter.toResponse(genTemplateDao.findByGroupId(groupId));
    }


}
