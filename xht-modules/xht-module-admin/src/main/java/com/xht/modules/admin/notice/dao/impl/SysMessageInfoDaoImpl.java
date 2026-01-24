package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysMessageInfoDao;
import com.xht.modules.admin.notice.dao.mapper.SysMessageInfoMapper;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;
import com.xht.modules.admin.notice.enums.MessageStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 系统管理-站内信收件人明细表 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysMessageInfoDaoImpl extends MapperRepositoryImpl<SysMessageInfoMapper, SysMessageInfoEntity> implements SysMessageInfoDao {

    /**
     * 已读站内信（收件人侧）
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     */
    @Override
    public void updateReadById(Long messageId, Long userId) {
        LambdaUpdateWrapper<SysMessageInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMessageInfoEntity::getMessageStatus, MessageStatusEnums.READ);
        updateWrapper.set(SysMessageInfoEntity::getReadTime, LocalDateTime.now());
        updateWrapper.eq(SysMessageInfoEntity::getMessageId, messageId);
        updateWrapper.eq(SysMessageInfoEntity::getRecipientId, userId);
        updateWrapper.eq(SysMessageInfoEntity::getMessageStatus, MessageStatusEnums.UNREAD);
        update(updateWrapper);
    }

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     */
    @Override
    public void updateRemoveById(Long messageId, Long userId) {
        LambdaUpdateWrapper<SysMessageInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMessageInfoEntity::getMessageStatus, MessageStatusEnums.REMOVE);
        updateWrapper.set(SysMessageInfoEntity::getRemoveTime, LocalDateTime.now());
        updateWrapper.eq(SysMessageInfoEntity::getMessageId, messageId);
        updateWrapper.eq(SysMessageInfoEntity::getRecipientId, userId);
        update(updateWrapper);
    }

    /**
     * 撤回站内信（发件人侧）
     *
     * @param messageId 站内信ID
     */
    @Override
    public void updateCancelById(Long messageId) {
        LambdaUpdateWrapper<SysMessageInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysMessageInfoEntity::getMessageStatus, MessageStatusEnums.CANCEL);
        updateWrapper.set(SysMessageInfoEntity::getCancelTime, LocalDateTime.now());
        updateWrapper.eq(SysMessageInfoEntity::getMessageId, messageId);
        update(updateWrapper);
    }

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 站内信发送详情
     */
    @Override
    public Page<SysMessageInfoEntity> findAdminPageSend(Page<SysMessageInfoEntity> page, SysMessageInfoQuery query) {
        LambdaQueryWrapper<SysMessageInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMessageInfoEntity::getMessageId, query.getMessageId());
        queryWrapper.eq(condition(query.getMessageStatus()), SysMessageInfoEntity::getMessageStatus, query.getMessageStatus());
        queryWrapper.eq(condition(query.getMessageStar()), SysMessageInfoEntity::getMessageStar, query.getMessageStar());
        return page(page, queryWrapper);
    }

    /**
     * 分页查询我接收的站内信
     *
     * @param page  分页参数
     * @param query 查询参数
     */
    @Override
    public Page<SysMessageInfoEntity> findMyMessagePageList(Page<SysMessageInfoEntity> page, SysMessageInfoQuery query) {
        LambdaQueryWrapper<SysMessageInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysMessageInfoEntity::getMessageStatus, MessageStatusEnums.CANCEL);
        queryWrapper.eq(SysMessageInfoEntity::getRecipientId, query.getRecipientId());
        queryWrapper.eq(condition(query.getMessageStatus()), SysMessageInfoEntity::getMessageStatus, query.getMessageStatus());
        queryWrapper.eq(condition(query.getMessageStar()), SysMessageInfoEntity::getMessageStar, query.getMessageStar());
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysMessageInfoEntity, ?> getFieldId() {
        return SysMessageInfoEntity::getId;
    }

}
