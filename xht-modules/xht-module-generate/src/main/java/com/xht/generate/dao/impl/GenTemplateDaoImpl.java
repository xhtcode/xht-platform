package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
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
        updateWrapper.set(condition(formRequest.getGroupId()), GenTemplateEntity::getGroupId, formRequest.getGroupId());
        updateWrapper.set(condition(formRequest.getName()), GenTemplateEntity::getName, formRequest.getName());
        updateWrapper.set(condition(formRequest.getContent()), GenTemplateEntity::getContent, formRequest.getContent());
        updateWrapper.set(condition(formRequest.getFilePathTemplate()), GenTemplateEntity::getFilePathTemplate, formRequest.getFilePathTemplate());
        updateWrapper.set(condition(formRequest.getFileNameTemplate()), GenTemplateEntity::getFileNameTemplate, formRequest.getFileNameTemplate());
        updateWrapper.eq(GenTemplateEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTemplateEntity, ?> getFieldId() {
        return GenTemplateEntity::getId;
    }
}
