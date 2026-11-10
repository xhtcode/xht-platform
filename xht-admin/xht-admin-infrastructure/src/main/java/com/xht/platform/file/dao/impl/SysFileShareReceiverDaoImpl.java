package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileShareReceiverDao;
import com.xht.platform.file.dao.mapper.SysFileShareReceiverMapper;
import com.xht.platform.file.domain.query.SysFileShareReceivedQuery;
import com.xht.platform.file.entity.SysFileShareReceiverEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件分享接收人数据访问层实现
 *
 * @author xht
 **/
@Repository
public class SysFileShareReceiverDaoImpl extends MapperRepositoryImpl<SysFileShareReceiverMapper, SysFileShareReceiverEntity> implements SysFileShareReceiverDao {

    @Override
    public Optional<SysFileShareReceiverEntity> findByShareAndReceiver(Long shareId, Long receiverId) {
        LambdaQueryWrapper<SysFileShareReceiverEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileShareReceiverEntity::getShareId, shareId);
        wrapper.eq(SysFileShareReceiverEntity::getReceiverId, receiverId);
        return Optional.ofNullable(getOne(wrapper));
    }

    @Override
    public Page<SysFileShareReceiverEntity> findReceivedPage(Page<SysFileShareReceiverEntity> page, SysFileShareReceivedQuery query) {
        LambdaQueryWrapper<SysFileShareReceiverEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getReceiverId() != null, SysFileShareReceiverEntity::getReceiverId, query.getReceiverId());
        wrapper.eq(query.getReadStatus() != null, SysFileShareReceiverEntity::getReadStatus, query.getReadStatus());
        wrapper.eq(SysFileShareReceiverEntity::getHidden, 0);
        wrapper.orderByAsc(SysFileShareReceiverEntity::getReadStatus);
        wrapper.orderByDesc(SysFileShareReceiverEntity::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public long countUnread(Long receiverId) {
        LambdaQueryWrapper<SysFileShareReceiverEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileShareReceiverEntity::getReceiverId, receiverId);
        wrapper.eq(SysFileShareReceiverEntity::getReadStatus, 0);
        wrapper.eq(SysFileShareReceiverEntity::getHidden, 0);
        return count(wrapper);
    }

    @Override
    public void markRead(Long shareId, Long receiverId) {
        LambdaUpdateWrapper<SysFileShareReceiverEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileShareReceiverEntity::getReadStatus, 1);
        wrapper.set(SysFileShareReceiverEntity::getReadTime, LocalDateTime.now());
        wrapper.eq(SysFileShareReceiverEntity::getShareId, shareId);
        wrapper.eq(SysFileShareReceiverEntity::getReceiverId, receiverId);
        wrapper.eq(SysFileShareReceiverEntity::getReadStatus, 0);
        getBaseMapper().update(null, wrapper);
    }

    @Override
    public List<SysFileShareReceiverEntity> findByShareId(Long shareId) {
        return findList(SysFileShareReceiverEntity::getShareId, shareId);
    }

    @Override
    protected SFunction<SysFileShareReceiverEntity, ?> getFieldId() {
        return SysFileShareReceiverEntity::getId;
    }

}
