package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.dao.mapper.GenTemplateMapper;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.form.GenTemplateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xht.framework.mybatis.constant.MapperConstant.JACKSON_TYPE_HANDLER;

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
     * @param form 菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(GenTemplateForm form) {
        LambdaUpdateWrapper<GenTemplateEntity> updateWrapper = lambdaUpdateWrapper();
        // @formatter:off
        updateWrapper
                .set(condition(form.getGroupId()), GenTemplateEntity::getGroupId, form.getGroupId())
                .set(condition(form.getTemplateName()), GenTemplateEntity::getTemplateName, form.getTemplateName())
                .set(condition(form.getTemplateContent()), GenTemplateEntity::getTemplateContent, form.getTemplateContent())
                .set(condition(form.getTemplateFilePath()), GenTemplateEntity::getTemplateFilePath, form.getTemplateFilePath())
                .set(condition(form.getTemplateFileName()), GenTemplateEntity::getTemplateFileName, form.getTemplateFileName())
                .set(condition(form.getTemplateFileType()), GenTemplateEntity::getTemplateFileType, form.getTemplateFileType())
                .set(condition(form.getTemplateIgnoreField()), GenTemplateEntity::getTemplateIgnoreField, form.getTemplateIgnoreField(), JACKSON_TYPE_HANDLER)
                .set(condition(form.getTemplateSort()), GenTemplateEntity::getTemplateSort, form.getTemplateSort())
                .eq(GenTemplateEntity::getId, form.getId());
        // @formatter:on
        update(updateWrapper);
    }

    /**
     * 根据分组查询模板信息
     *
     * @param groupId 分组ID
     * @return 模板信息
     */
    @Override
    public List<GenTemplateEntity> findByGroupId(String groupId) {
        // @formatter:off
        LambdaQueryWrapper<GenTemplateEntity> queryWrapper = lambdaQueryWrapper()
                .eq(GenTemplateEntity::getGroupId, groupId)
                .orderByDesc(GenTemplateEntity::getGroupId);
        // @formatter:on
        return list(queryWrapper);
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
