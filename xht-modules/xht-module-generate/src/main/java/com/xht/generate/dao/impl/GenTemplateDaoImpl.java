package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateDao;
import com.xht.generate.dao.mapper.GenTemplateMapper;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.form.GenTemplateFormRequest;
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
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(GenTemplateFormRequest formRequest) {
        LambdaUpdateWrapper<GenTemplateEntity> updateWrapper = lambdaUpdateWrapper();
        // @formatter:off
        updateWrapper
                .set(condition(formRequest.getGroupId()), GenTemplateEntity::getGroupId, formRequest.getGroupId())
                .set(condition(formRequest.getTemplateName()), GenTemplateEntity::getTemplateName, formRequest.getTemplateName())
                .set(condition(formRequest.getTemplateContent()), GenTemplateEntity::getTemplateContent, formRequest.getTemplateContent())
                .set(condition(formRequest.getTemplateFilePath()), GenTemplateEntity::getTemplateFilePath, formRequest.getTemplateFilePath())
                .set(condition(formRequest.getTemplateFileName()), GenTemplateEntity::getTemplateFileName, formRequest.getTemplateFileName())
                .set(condition(formRequest.getTemplateFileType()), GenTemplateEntity::getTemplateFileType, formRequest.getTemplateFileType())
                .set(condition(formRequest.getTemplateIgnoreField()), GenTemplateEntity::getTemplateIgnoreField, formRequest.getTemplateIgnoreField(), JACKSON_TYPE_HANDLER)
                .set(condition(formRequest.getTemplateSort()), GenTemplateEntity::getTemplateSort, formRequest.getTemplateSort())
                .eq(GenTemplateEntity::getId, formRequest.getId());
        // @formatter:on
        return update(updateWrapper);
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
