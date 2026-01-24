package com.xht.modules.admin.notice.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.domain.response.SysMessageInfoResponse;
import com.xht.modules.admin.notice.domain.response.SysMessageResponse;
import com.xht.platform.common.notice.core.MessagePayload;

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
     * 已读站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    void updateReadById(Long messageId);

    /**
     * 删除站内信（收件人侧）
     *
     * @param messageId 站内信ID
     */
    void updateRemoveById(Long messageId);

    /**
     * 撤回站内信（发件人侧）
     *
     * @param messageId 站内信ID
     */
    void updateCancelById(Long messageId);

    /**
     * 查询站内信详情
     *
     * @param messageId 站内信ID
     */
    SysMessageInfoResponse findById(Long messageId);

    /**
     * 分页查询我发送的站内信
     *
     * @param query 站内信参数
     */
    PageResponse<SysMessageResponse> findMyPageSend(SysMessageQuery query);

    /**
     * 管理员分页查询站内信
     *
     * @param query 查询参数
     */
    PageResponse<SysMessageResponse> findAdminPage(SysMessageQuery query);

    /**
     * 管理员分页查看站内信发送详情
     *
     * @param query 查询参数
     * @return 站内信发送详情
     */
    PageResponse<SysMessageInfoResponse> findAdminPageSend(SysMessageInfoQuery query);

    /**
     * 分页查询我接收的站内信
     *
     * @param query 查询参数
     */
    PageResponse<SysMessageInfoResponse> findMyPage(SysMessageInfoQuery query);

}
