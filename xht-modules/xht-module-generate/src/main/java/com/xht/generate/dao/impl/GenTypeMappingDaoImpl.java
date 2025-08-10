package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTypeMappingDao;
import com.xht.generate.dao.mapper.GenTypeMappingMapper;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.request.GenTypeMappingFormRequest;
import com.xht.generate.domain.request.GenTypeMappingQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 字段映射管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTypeMappingDaoImpl extends MapperRepositoryImpl<GenTypeMappingMapper, GenTypeMappingEntity> implements GenTypeMappingDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenTypeMappingFormRequest formRequest) {
        LambdaUpdateWrapper<GenTypeMappingEntity> updateWrapper = lambdaUpdateWrapper();
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
    public Page<GenTypeMappingEntity> queryPageRequest(Page<GenTypeMappingEntity> page, GenTypeMappingQueryRequest queryRequest) {
        LambdaQueryWrapper<GenTypeMappingEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(Objects.nonNull(queryRequest.getDbType()), GenTypeMappingEntity::getDbType, queryRequest.getDbType())
                .eq(Objects.nonNull(queryRequest.getTargetLanguage()), GenTypeMappingEntity::getTargetLanguage, queryRequest.getTargetLanguage());
        return page(page, queryWrapper);
    }
}
