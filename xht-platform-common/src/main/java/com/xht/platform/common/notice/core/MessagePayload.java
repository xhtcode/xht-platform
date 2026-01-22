package com.xht.platform.common.notice.core;

import com.xht.platform.common.notice.enums.MessageTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 描述 ：消息载体内容
 *
 * @author xht
 **/
@Getter
@SuppressWarnings("all")
@AllArgsConstructor
public final class MessagePayload {

    /**
     * 发件人信息
     */
    private final MessageUser sendUser;

    /**
     * 收件人 信息
     */
    private final List<MessageUser> recipientUser;

    /**
     * 消息类型：1-系统通知 2-业务提醒
     */
    private final MessageTypeEnums messageType;

    /**
     * 消息标题
     */
    private final String messageTitle;

    /**
     * 消息内容（支持富文本）
     */
    private final String messageContent;

    /**
     * 消息扩展信息（如关联订单ID、跳转链接）
     */
    private final MessageExtendInfo messageExtend;

}
