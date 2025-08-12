package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.dao.mapper.GenTemplateMapper;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenTemplateFormRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 模板管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTemplateDaoImpl extends MapperRepositoryImpl<GenTemplateMapper, GenTemplateEntity> implements GenTemplateDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(GenTemplateFormRequest formRequest) {
        LambdaUpdateWrapper<GenTemplateEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(Objects.nonNull(formRequest.getGroupId()), GenTemplateEntity::getGroupId, formRequest.getGroupId());
        updateWrapper.set(StringUtils.hasText(formRequest.getName()), GenTemplateEntity::getName, formRequest.getName());
        updateWrapper.set(StringUtils.hasText(formRequest.getContent()), GenTemplateEntity::getContent, formRequest.getContent());
        updateWrapper.set(StringUtils.hasText(formRequest.getFileType()), GenTemplateEntity::getFileType, formRequest.getFileType());
        updateWrapper.set(StringUtils.hasText(formRequest.getFilePathTemplate()), GenTemplateEntity::getFilePathTemplate, formRequest.getFilePathTemplate());
        updateWrapper.set(StringUtils.hasText(formRequest.getFileNameTemplate()), GenTemplateEntity::getFileNameTemplate, formRequest.getFileNameTemplate());
        updateWrapper.eq(GenTemplateEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

}
