package com.xht.modules.generate.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.modules.common.constant.GenConstant;
import com.xht.modules.generate.converter.GenTemplateConverter;
import com.xht.modules.generate.dao.GenTemplateDao;
import com.xht.modules.generate.dao.GenTemplateGroupDao;
import com.xht.modules.generate.domain.form.GenTemplateForm;
import com.xht.modules.generate.domain.response.GenTemplateResponse;
import com.xht.modules.generate.entity.GenTemplateEntity;
import com.xht.modules.generate.entity.GenTemplateGroupEntity;
import com.xht.modules.generate.service.IGenTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


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
     * @param form 模板表单请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(GenTemplateForm form) {
        formatTemplate(form);
        GenTemplateGroupEntity groupEntity = genTemplateGroupDao.findOneOptional(GenTemplateGroupEntity::getId, form.getGroupId()).orElseThrow(() -> new BusinessException("模板组不存在"));
        GenTemplateEntity entity = genTemplateConverter.toEntity(form);
        genTemplateDao.save(entity);
        genTemplateGroupDao.updateTemplateCountById(groupEntity.getId(), groupEntity.getTemplateCount(), groupEntity.getTemplateCount() + 1);
        return entity.getId();
    }


    /**
     * 根据ID删除模板
     *
     * @param id 模板ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        GenTemplateEntity genTemplate = genTemplateDao.findById(id);
        if (Objects.nonNull(genTemplate)) {
            GenTemplateGroupEntity groupEntity = genTemplateGroupDao.findOneOptional(GenTemplateGroupEntity::getId, genTemplate.getGroupId()).orElseThrow(() -> new BusinessException("模板组不存在"));
            genTemplateDao.removeById(id);
            genTemplateGroupDao.updateTemplateCountById(groupEntity.getId(), groupEntity.getTemplateCount(), groupEntity.getTemplateCount() - 1);
        }
    }

    /**
     * 根据ID更新模板
     *
     * @param form 模板更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(GenTemplateForm form) {
        formatTemplate(form);
        Boolean exists = genTemplateGroupDao.exists(GenTemplateGroupEntity::getId, form.getGroupId());
        ThrowUtils.throwIf(!exists, BusinessErrorCode.DATA_NOT_EXIST, "模板组不存在");
        Boolean menuExists = genTemplateDao.exists(GenTemplateEntity::getId, form.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "模板不存在");
        genTemplateDao.updateFormRequest(form);
    }

    private void formatTemplate(GenTemplateForm form) {
        form.setTemplateFilePath(StrUtil.addSuffixIfNot(form.getTemplateFilePath(), GenConstant.PATH_SEPARATOR));
        form.setTemplateFilePath(StrUtil.addPrefixIfNot(form.getTemplateFilePath(), GenConstant.PATH_SEPARATOR));
        form.setTemplateFileName(StrUtil.removeAllPrefix(form.getTemplateFileName(), GenConstant.PATH_SEPARATOR));
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
