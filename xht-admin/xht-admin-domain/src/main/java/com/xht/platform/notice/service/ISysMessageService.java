package com.xht.platform.notice.service;

import com.xht.framework.common.domain.response.PageResponse;
import  com.xht.platform.notice.domain.query.SysMessageInfoQuery;
import  com.xht.platform.notice.domain.query.SysMessageQuery;
import  com.xht.platform.notice.domain.response.SysMessageResponse;
import  com.xht.platform.notice.domain.vo.MessageInfoVO;
import  com.xht.platform.notice.domain.vo.MessagePageVO;
import  com.xht.platform.notice.enums.MessageStarEnum;
import  com.xht.platform.notice.enums.MessageTopEnum;
import com.xht.framework.core.message.core.MessagePayload;

/**
 * 系统管理-站内信主表 Service
 *
 * @author xht
 */
public interface ISysMessageService {

    /**
     * 发送站内信
     *
     * @param payload 站内信参数
     */
    void sendMessage(MessagePayload payload);

    /**
     * 已读所有站内信（收件人侧）
     */
    void updateReadAll();

    /**
     * 已读站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    void updateReadById(Long messageId);

    /**
     * 收藏站内信（收件人侧）
     * @param messageId 站内信ID
     * @param messageStarEnum  站内信收藏枚举
     */
    void updateStartById(Long messageId, MessageStarEnum messageStarEnum);

    /**
     * 置顶站内信（收件人侧）
     * @param messageId 站内信ID
     * @param messageTopEnum  站内信置顶枚举
     */
    void updateTopById(Long messageId, MessageTopEnum messageTopEnum);

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    void updateRemoveById(Long messageId);

    /**
     * 撤回站内信（全部）
     *
     * @param messageId 站内信ID
     */
    void updateCancelAllById(Long messageId);

    /**
     * 撤回站内信 （对用户单一撤回）
     * @param messageInfoId 站内信详情ID
     */
    void updateCancelSingleByInfoId(Long messageInfoId);

    /**
     * 查询站内信详情
     *
     * @param messageId 站内信ID
     */
    MessageInfoVO findInfoByMessageId(Long messageId);

    /**
     * 管理员分页查询站内信
     *
     * @param query      站内信查询参数
     * @return            站内信分页列表
     */
    PageResponse<SysMessageResponse> findAdminPage(SysMessageQuery query);

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param query 查询参数
     * @return 站内信发送详情
     */
    MessagePageVO findAdminPageSend(SysMessageInfoQuery query);

    /**
     * 分页查询我接收的站内信
     *
     * @param query 查询参数
     * @return  站内信分页列表
     */
    PageResponse<MessageInfoVO> findMyPage(SysMessageInfoQuery query);

}
