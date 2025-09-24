package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTemplateGroupDao;
import com.xht.generate.dao.mapper.GenTemplateGroupMapper;
import com.xht.generate.domain.entity.GenTemplateGroupEntity;
import com.xht.generate.domain.form.GenTemplateGroupFormRequest;
import com.xht.generate.domain.query.GenTemplateGroupQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        updateWrapper.set(condition(formRequest.getGroupSort()), GenTemplateGroupEntity::getGroupSort, formRequest.getGroupSort());
        updateWrapper.set(condition(formRequest.getGroupDesc()), GenTemplateGroupEntity::getGroupDesc, formRequest.getGroupDesc());
        updateWrapper.eq(GenTemplateGroupEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 根据提供的查询请求参数分页查询代码生成模板组信息
     *
     * @param page         分页查询参数
     * @param queryRequest 查询参数
     * @return 代码生成模板组列表响应结果
     */
    @Override
    public Page<GenTemplateGroupEntity> queryPageRequest(Page<GenTemplateGroupEntity> page, GenTemplateGroupQueryRequest queryRequest) {
        LambdaQueryWrapper<GenTemplateGroupEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(condition(queryRequest.getGroupName()), GenTemplateGroupEntity::getGroupName, queryRequest.getGroupName());
        return page(page, queryWrapper);
    }

    /**
     * 查询所有
     *
     * @return 列表
     */
    @Override
    public List<GenTemplateGroupEntity> findAllBy() {
        LambdaQueryWrapper<GenTemplateGroupEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.select(
                GenTemplateGroupEntity::getId,
                GenTemplateGroupEntity::getGroupName,
                GenTemplateGroupEntity::getTemplateCount,
                GenTemplateGroupEntity::getGroupSort
        );
        queryWrapper.orderByDesc(GenTemplateGroupEntity::getGroupSort);
        return list(queryWrapper);
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
