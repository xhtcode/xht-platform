package com.xht.modules.admin.notice.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.modules.admin.notice.converter.SysMessageConverter;
import com.xht.modules.admin.notice.converter.SysMessageInfoConverter;
import com.xht.modules.admin.notice.dao.SysMessageDao;
import com.xht.modules.admin.notice.dao.SysMessageInfoDao;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.domain.response.SysMessageResponse;
import com.xht.modules.admin.notice.domain.vo.MessageInfoVo;
import com.xht.modules.admin.notice.domain.vo.MessagePageVo;
import com.xht.modules.admin.notice.entity.SysMessageEntity;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;
import com.xht.modules.admin.notice.enums.MessageStarEnums;
import com.xht.modules.admin.notice.enums.MessageStatusEnums;
import com.xht.modules.admin.notice.enums.MessageTopEnums;
import com.xht.modules.admin.notice.service.ISysMessageService;
import com.xht.platform.common.notice.core.MessageExtendInfo;
import com.xht.platform.common.notice.core.MessagePayload;
import com.xht.platform.common.notice.core.MessageUser;
import com.xht.platform.common.notice.enums.MessageTypeEnums;
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
        MessageTypeEnums messageType = payload.getMessageType();
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
            infoEntity.setMessageStatus(MessageStatusEnums.UNREAD);
            infoEntity.setMessageTop(MessageTopEnums.NO);
            infoEntity.setMessageStar(MessageStarEnums.NO);
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
     * @param messageStarEnums 站内信收藏枚举
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStartById(Long messageId, MessageStarEnums messageStarEnums) {
        sysMessageInfoDao.updateStartById(messageId, SecurityUtils.getUserId(), messageStarEnums);
    }

    /**
     * 置顶站内信（收件人侧）
     *
     * @param messageId       站内信ID
     * @param messageTopEnums 站内信置顶枚举
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTopById(Long messageId, MessageTopEnums messageTopEnums) {
        sysMessageInfoDao.updateTopById(messageId, SecurityUtils.getUserId(), messageTopEnums);
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
    public MessageInfoVo findInfoByMessageId(Long messageId) {
        MessageInfoVo messageInfoVo = sysMessageInfoDao.findInfoByMessageId(messageId, SecurityUtils.getUserId());
        Optional.ofNullable(messageInfoVo)
                .map(MessageInfoVo::getResponse)
                .ifPresentOrElse(response -> {
                    if (Objects.equals(MessageStatusEnums.UNREAD, response.getMessageStatus())) {
                        response.setMessageStatus(MessageStatusEnums.READ);
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
    public MessagePageVo findAdminPageSend(SysMessageInfoQuery query) {
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
    public PageResponse<MessageInfoVo> findMyPage(SysMessageInfoQuery query) {
        query.setRecipientId(SecurityUtils.getUserId());
        Page<MessageInfoVo> page = sysMessageInfoDao.findMyMessagePageList(PageTool.getPage(query), query);
        return PageTool.getPageVo(page);
    }

}




