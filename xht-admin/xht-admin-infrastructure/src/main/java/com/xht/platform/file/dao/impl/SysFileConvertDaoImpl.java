package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileConvertDao;
import com.xht.platform.file.dao.mapper.SysFileConvertMapper;
import com.xht.platform.file.entity.SysFileConvertEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件转换任务数据访问层实现
 *
 * @author xht
 **/
@Repository
public class SysFileConvertDaoImpl extends MapperRepositoryImpl<SysFileConvertMapper, SysFileConvertEntity> implements SysFileConvertDao {

    @Override
    public Optional<SysFileConvertEntity> findByFileAndTarget(Long fileId, String targetFormat) {
        LambdaQueryWrapper<SysFileConvertEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileConvertEntity::getFileId, fileId);
        wrapper.eq(SysFileConvertEntity::getTargetFormat, targetFormat);
        return Optional.ofNullable(getOne(wrapper));
    }

    @Override
    public List<SysFileConvertEntity> findExpiredSuccess(LocalDateTime now, int limit) {
        LambdaQueryWrapper<SysFileConvertEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileConvertEntity::getStatus, 2);
        wrapper.lt(SysFileConvertEntity::getExpireTime, now);
        wrapper.orderByAsc(SysFileConvertEntity::getExpireTime);
        wrapper.last("LIMIT " + limit);
        return list(wrapper);
    }

    @Override
    public List<SysFileConvertEntity> findByFileId(Long fileId) {
        return findList(SysFileConvertEntity::getFileId, fileId);
    }

    @Override
    protected SFunction<SysFileConvertEntity, ?> getFieldId() {
        return SysFileConvertEntity::getId;
    }

}
