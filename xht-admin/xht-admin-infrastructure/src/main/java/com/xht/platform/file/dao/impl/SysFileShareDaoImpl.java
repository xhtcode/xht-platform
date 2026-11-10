package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileShareDao;
import com.xht.platform.file.dao.mapper.SysFileShareMapper;
import com.xht.platform.file.domain.query.SysFileShareQuery;
import com.xht.platform.file.entity.SysFileShareEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述： 文件分享数据访问层实现
 *
 * @author xht
 **/
@Repository
public class SysFileShareDaoImpl extends MapperRepositoryImpl<SysFileShareMapper, SysFileShareEntity> implements SysFileShareDao {

    @Override
    public Optional<SysFileShareEntity> findByToken(String shareToken) {
        return findOneOptional(SysFileShareEntity::getShareToken, shareToken);
    }

    @Override
    public Page<SysFileShareEntity> findPageByCreator(Page<SysFileShareEntity> page, SysFileShareQuery query) {
        LambdaQueryWrapper<SysFileShareEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getCreatorId() != null, SysFileShareEntity::getCreatorId, query.getCreatorId());
        wrapper.eq(query.getShareMode() != null, SysFileShareEntity::getShareMode, query.getShareMode());
        wrapper.eq(query.getPermission() != null, SysFileShareEntity::getPermission, query.getPermission());
        wrapper.eq(query.getStatus() != null, SysFileShareEntity::getStatus, query.getStatus());
        wrapper.ge(query.getStartTime() != null, SysFileShareEntity::getCreateTime, query.getStartTime());
        wrapper.le(query.getEndTime() != null, SysFileShareEntity::getCreateTime, query.getEndTime());
        wrapper.orderByDesc(SysFileShareEntity::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public int incrementAccessCount(Long shareId) {
        LambdaUpdateWrapper<SysFileShareEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.setSql("access_count = access_count + 1");
        wrapper.eq(SysFileShareEntity::getId, shareId);
        return getBaseMapper().update(null, wrapper);
    }

    @Override
    public void updateLastAccess(Long shareId, String ip) {
        LambdaUpdateWrapper<SysFileShareEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileShareEntity::getLastAccessTime, LocalDateTime.now());
        wrapper.set(StringUtils.hasText(ip), SysFileShareEntity::getLastAccessIp, ip);
        wrapper.eq(SysFileShareEntity::getId, shareId);
        getBaseMapper().update(null, wrapper);
    }

    @Override
    protected SFunction<SysFileShareEntity, ?> getFieldId() {
        return SysFileShareEntity::getId;
    }

    @Override
    public LocalDateTime findMaxActiveExpire(Long fileId, LocalDateTime now) {
        LambdaQueryWrapper<SysFileShareEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileShareEntity::getFileId, fileId);
        wrapper.eq(SysFileShareEntity::getStatus, 1);
        wrapper.gt(SysFileShareEntity::getExpireTime, now);
        wrapper.select(SysFileShareEntity::getExpireTime);
        return list(wrapper).stream()
                .map(SysFileShareEntity::getExpireTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

}
