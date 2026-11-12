package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileUsedDao;
import com.xht.platform.file.dao.mapper.SysFileUsedMapper;
import com.xht.platform.file.domain.query.SysFileUsedQuery;
import com.xht.platform.file.entity.SysFileUsedEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件用途配置数据访问层实现
 *
 * @author xht
 **/
@Repository
public class SysFileUsedDaoImpl extends MapperRepositoryImpl<SysFileUsedMapper, SysFileUsedEntity> implements SysFileUsedDao {

    @Override
    public Optional<SysFileUsedEntity> findByCode(String usedCode) {
        return findOneOptional(SysFileUsedEntity::getUsedCode, usedCode);
    }

    @Override
    public Page<SysFileUsedEntity> findPageList(Page<SysFileUsedEntity> page, SysFileUsedQuery query) {
        LambdaQueryWrapper<SysFileUsedEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(condition(query.getUsedCode()), SysFileUsedEntity::getUsedCode, query.getUsedCode());
        wrapper.like(condition(query.getUsedName()), SysFileUsedEntity::getUsedName, query.getUsedName());
        wrapper.eq(condition(query.getEnabled()), SysFileUsedEntity::getEnabled, query.getEnabled());
        wrapper.eq(condition(query.getBuiltIn()), SysFileUsedEntity::getBuiltIn, query.getBuiltIn());
        wrapper.orderByAsc(SysFileUsedEntity::getSort);
        wrapper.orderByAsc(SysFileUsedEntity::getId);
        return page(page, wrapper);
    }

    @Override
    public List<SysFileUsedEntity> findAllEnabled() {
        LambdaQueryWrapper<SysFileUsedEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileUsedEntity::getEnabled, 1);
        wrapper.orderByAsc(SysFileUsedEntity::getSort);
        return list(wrapper);
    }

    @Override
    public boolean existsByCode(String usedCode, Long excludeId) {
        LambdaQueryWrapper<SysFileUsedEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileUsedEntity::getUsedCode, usedCode);
        wrapper.ne(excludeId != null, SysFileUsedEntity::getId, excludeId);
        return count(wrapper) > 0;
    }

    @Override
    protected SFunction<SysFileUsedEntity, ?> getFieldId() {
        return SysFileUsedEntity::getId;
    }

}
