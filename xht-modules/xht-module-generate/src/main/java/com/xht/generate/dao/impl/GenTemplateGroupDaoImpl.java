package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateGroupDao;
import com.xht.generate.dao.mapper.GenTemplateGroupMapper;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.request.GenTemplateGroupFormRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTemplateGroupDaoImpl extends MapperRepositoryImpl<GenTemplateGroupMapper, GenTemplateGroupEntity> implements GenTemplateGroupDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(GenTemplateGroupFormRequest formRequest) {
        LambdaUpdateWrapper<GenTemplateGroupEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(condition(formRequest.getGroupName()), GenTemplateGroupEntity::getGroupName, formRequest.getGroupName());
        updateWrapper.set(condition(formRequest.getGroupDesc()), GenTemplateGroupEntity::getGroupDesc, formRequest.getGroupDesc());
        updateWrapper.eq(GenTemplateGroupEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTemplateGroupEntity, ?> getFieldId() {
        return GenTemplateGroupEntity::getId;
    }
}
