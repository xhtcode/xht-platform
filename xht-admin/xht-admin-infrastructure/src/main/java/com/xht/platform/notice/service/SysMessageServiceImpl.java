package com.xht.platform.notice.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import  com.xht.platform.notice.converter.SysMessageConverter;
import  com.xht.platform.notice.converter.SysMessageInfoConverter;
import  com.xht.platform.notice.dao.SysMessageDao;
import  com.xht.platform.notice.dao.SysMessageInfoDao;
import  com.xht.platform.notice.domain.query.SysMessageInfoQuery;
import  com.xht.platform.notice.domain.query.SysMessageQuery;
import  com.xht.platform.notice.domain.response.SysMessageResponse;
import  com.xht.platform.notice.domain.vo.MessageInfoVO;
import  com.xht.platform.notice.domain.vo.MessagePageVO;
import  com.xht.platform.notice.entity.SysMessageEntity;
import  com.xht.platform.notice.entity.SysMessageInfoEntity;
import  com.xht.platform.notice.enums.MessageStarEnum;
import  com.xht.platform.notice.enums.MessageStatusEnum;
import  com.xht.platform.notice.enums.MessageTopEnum;
import com.xht.framework.core.message.core.MessageExtendInfo;
import com.xht.framework.core.message.core.MessagePayload;
import com.xht.framework.core.message.core.MessageUser;
import com.xht.framework.core.message.enums.MessageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 系统管理-站内信主表 Service
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMessageServiceImpl implements ISysMessageService {

    private final SysMessageDao sysMessageDao;

    private final SysMessageInfoDao sysMessageInfoDao;

    private final SysMessageConverter sysMessageConverter;

    private final SysMessageInfoConverter sysMessageInfoConverter;

    private final TransactionTemplate transactionTemplate;

    /**
     * 发送站内信
     *
     * @param payload 站内信参数
     */
    @Async
    @Override
    public void sendMessage(MessagePayload payload) {
        MessageUser sendUser = payload.getSendUser();
        List<MessageUser> recipientUser = payload.getRecipientUser();
        MessageTypeEnum messageType = payload.getMessageType();
        long messageId = IdUtil.getSnowflakeNextId();
        SysMessageEntity entity = new SysMessageEntity();
        entity.setId(messageId);
        entity.setSenderId(sendUser.userId());
        entity.setSenderName(sendUser.userName());
        entity.setMessageType(messageType);
        entity.setMessageTitle(payload.getMessageTitle());
        entity.setMessageContent(payload.getMessageContent());
        entity.setMessageExtend(new MessageExtendInfo());
        List<SysMessageInfoEntity> messageInfoEntities = new ArrayList<>();
        for (MessageUser messageUser : recipientUser) {
            SysMessageInfoEntity infoEntity = new SysMessageInfoEntity();
            infoEntity.setMessageId(messageId);
            infoEntity.setRecipientId(messageUser.userId());
            infoEntity.setRecipientName(messageUser.userName());
            infoEntity.setMessageStatus(MessageStatusEnum.UNREAD);
            infoEntity.setMessageTop(MessageTopEnum.NO);
            infoEntity.setMessageStar(MessageStarEnum.NO);
            messageInfoEntities.add(infoEntity);
        }
        transactionTemplate.execute(status -> {
            try {
                sysMessageDao.save(entity);
                if (!CollectionUtils.isEmpty(messageInfoEntities)) {
                    sysMessageInfoDao.saveAll(messageInfoEntities);
                }
                return Boolean.TRUE;
            } catch (Exception e) {
                status.setRollbackOnly();
                log.info("发送站内信失败：{}", e.getMessage(), e);
            }
            return Boolean.FALSE;
        });
    }

    /**
     * 已读所有站内信（收件人侧）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReadAll() {
        Long userId = SecurityUtils.getUserId();
        sysMessageInfoDao.updateReadById(null, userId);
    }

    /**
     * 已读站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReadById(Long messageId) {
        sysMessageInfoDao.updateReadById(messageId, SecurityUtils.getUserId());
    }

    /**
     * 收藏站内信（收件人侧）
     *
     * @param messageId        站内信ID
     * @param messageStarEnum 站内信收藏枚举
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStartById(Long messageId, MessageStarEnum messageStarEnum) {
        sysMessageInfoDao.updateStartById(messageId, SecurityUtils.getUserId(), messageStarEnum);
    }

    /**
     * 置顶站内信（收件人侧）
     *
     * @param messageId       站内信ID
     * @param messageTopEnum 站内信置顶枚举
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTopById(Long messageId, MessageTopEnum messageTopEnum) {
        sysMessageInfoDao.updateTopById(messageId, SecurityUtils.getUserId(), messageTopEnum);
    }

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRemoveById(Long messageId) {
        sysMessageInfoDao.updateRemoveById(messageId, SecurityUtils.getUserId());
    }

    /**
     * 撤回站内信（全部）
     *
     * @param messageId 站内信ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCancelAllById(Long messageId) {
        LocalDateTime cancelTime = LocalDateTime.now();
        sysMessageDao.updateCancelByMessageId(messageId, cancelTime);
        sysMessageInfoDao.updateCancelByMessageId(messageId, cancelTime);
    }

    /**
     * 撤回站内信 （对用户单一撤回）
     *
     * @param messageInfoId 站内信详情ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCancelSingleByInfoId(Long messageInfoId) {
        sysMessageInfoDao.updateCancelById(messageInfoId);
    }

    /**
     * 查询站内信详情
     *
     * @param messageId 站内信ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageInfoVO findInfoByMessageId(Long messageId) {
        MessageInfoVO messageInfoVo = sysMessageInfoDao.findInfoByMessageId(messageId, SecurityUtils.getUserId());
        Optional.ofNullable(messageInfoVo)
                .map(MessageInfoVO::getResponse)
                .ifPresentOrElse(response -> {
                    if (Objects.equals(MessageStatusEnum.UNREAD, response.getMessageStatus())) {
                        response.setMessageStatus(MessageStatusEnum.READ);
                        response.setReadTime(LocalDateTime.now());
                        sysMessageInfoDao.updateReadById(messageId, SecurityUtils.getUserId());
                    }
                }, () -> {
                    throw new BusinessException(BusinessErrorCode.DATA_NOT_EXIST);
                });
        return messageInfoVo;
    }

    /**
     * 管理员分页查询站内信
     *
     * @param query      站内信查询参数
     * @return            站内信分页列表
     */
    @Override
    public PageResponse<SysMessageResponse> findAdminPage(SysMessageQuery query) {
        Page<SysMessageEntity> page = sysMessageDao.findPageList(PageTool.getPage(query), query);
        return sysMessageConverter.toResponse(page);
    }

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param query 查询参数
     * @return 站内信发送详情
     */
    @Override
    public MessagePageVO findAdminPageSend(SysMessageInfoQuery query) {
        ThrowUtils.notNull(query.getMessageId(), "查询不到信息id");
        SysMessageEntity messageEntity = sysMessageDao.findById(query.getMessageId());
        Page<SysMessageInfoEntity> page = sysMessageInfoDao.findAdminPageSend(PageTool.getPage(query), query);
        return sysMessageInfoConverter.toMessageVo(messageEntity, page);
    }

    /**
     * 分页查询我接收的站内信
     *
     * @param query 查询参数
     * @return  站内信分页列表
     */
    @Override
    public PageResponse<MessageInfoVO> findMyPage(SysMessageInfoQuery query) {
        query.setRecipientId(SecurityUtils.getUserId());
        Page<MessageInfoVO> page = sysMessageInfoDao.findMyMessagePageList(PageTool.getPage(query), query);
        return PageTool.getPageVo(page);
    }

}




