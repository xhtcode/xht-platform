package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysMessageDao;
import com.xht.modules.admin.notice.dao.mapper.SysMessageMapper;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.entity.SysMessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 描述 ： 系统管理-站内信主表 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysMessageDaoImpl extends MapperRepositoryImpl<SysMessageMapper, SysMessageEntity> implements SysMessageDao {


    /**
     * 撤回站内信（全部）
     *
     * @param messageId  站内信ID
     * @param cancelTime 撤销时间
     */
    @Override
    public void updateCancelByMessageId(Long messageId, LocalDateTime cancelTime) {
        LambdaUpdateWrapper<SysMessageEntity> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(SysMessageEntity::getId, messageId);
        queryWrapper.set(SysMessageEntity::getCancelTime, cancelTime);
        update(queryWrapper);
    }

    /**
     * 管理员分页查询站内信
     *
     * @param page       分页参数
     * @param query      站内信查询参数
     * @return            站内信分页列表
     */
    @Override
    public Page<SysMessageEntity> findPageList(Page<SysMessageEntity> page, SysMessageQuery query) {
        LambdaQueryWrapper<SysMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.select(
                SysMessageEntity::getId,
                SysMessageEntity::getSenderName,
                SysMessageEntity::getMessageType,
                SysMessageEntity::getMessageTitle,
                SysMessageEntity::getCancelTime,
                SysMessageEntity::getCreateTime,
                SysMessageEntity::getUpdateTime,
                SysMessageEntity::getCreateBy,
                SysMessageEntity::getUpdateBy
        );
        // @formatter:on
        if (query.isQuick()) {
            // @formatter:off
            queryWrapper.and(
                    condition(query.getKeyWord()), wrapper -> wrapper.or()
                            .like(SysMessageEntity::getSenderName, query.getKeyWord())
                            .or()
                            .like(SysMessageEntity::getMessageTitle, query.getKeyWord())
            );
            // @formatter:on
        } else {
            queryWrapper.like(condition(query.getSenderName()), SysMessageEntity::getSenderName, query.getSenderName());
            queryWrapper.eq(condition(query.getMessageType()), SysMessageEntity::getMessageType, query.getMessageType());
            queryWrapper.like(condition(query.getMessageTitle()), SysMessageEntity::getMessageTitle, query.getMessageTitle());
        }
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysMessageEntity, ?> getFieldId() {
        return SysMessageEntity::getId;
    }

}
