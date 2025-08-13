package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTableInfoDao;
import com.xht.generate.dao.mapper.GenTableInfoMapper;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.request.GenTableInfoQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * 表信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTableInfoDaoImpl extends MapperRepositoryImpl<GenTableInfoMapper, GenTableInfoEntity> implements GenTableInfoDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenTableInfoFormRequest formRequest) {
        LambdaUpdateWrapper<GenTableInfoEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(condition(formRequest.getGroupId()), GenTableInfoEntity::getGroupId, formRequest.getGroupId());
        updateWrapper.set(condition(formRequest.getDataSourceId()), GenTableInfoEntity::getDataSourceId, formRequest.getDataSourceId());
        updateWrapper.set(condition(formRequest.getTableComment()), GenTableInfoEntity::getTableComment, formRequest.getTableComment());
        updateWrapper.set(condition(formRequest.getCodeName()), GenTableInfoEntity::getCodeName, formRequest.getCodeName());
        updateWrapper.set(condition(formRequest.getCodeComment()), GenTableInfoEntity::getCodeComment, formRequest.getCodeComment());
        updateWrapper.eq(GenTableInfoEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenTableInfoEntity> queryPageRequest(Page<GenTableInfoEntity> page, GenTableInfoQueryRequest queryRequest) {
        LambdaQueryWrapper<GenTableInfoEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(condition(queryRequest.getGroupId()), GenTableInfoEntity::getGroupId, queryRequest.getGroupId());
        queryWrapper.eq(condition(queryRequest.getDataSourceId()), GenTableInfoEntity::getDataSourceId, queryRequest.getDataSourceId());
        queryWrapper.like(condition(queryRequest.getTableName()), GenTableInfoEntity::getTableName, queryRequest.getTableName());
        queryWrapper.like(condition(queryRequest.getTableComment()), GenTableInfoEntity::getTableComment, queryRequest.getTableComment());
        return page(page, queryWrapper);
    }

    /**
     * 根据数据源id查询表名
     *
     * @param dataSourceId 数据源id
     * @return 表名
     */
    @Override
    public List<String> findTableNameByDbId(Long dataSourceId) {
        LambdaQueryWrapper<GenTableInfoEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.select(GenTableInfoEntity::getTableName);
        queryWrapper.eq(GenTableInfoEntity::getDataSourceId, dataSourceId);
        List<GenTableInfoEntity> list = list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(GenTableInfoEntity::getTableName).toList();
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTableInfoEntity, ?> getFieldId() {
        return GenTableInfoEntity::getId;
    }
}
