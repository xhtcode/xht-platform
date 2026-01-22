package com.xht.platform.common.notice;

import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.StringFormatter;
import com.xht.platform.common.notice.core.MessageExtendInfo;
import com.xht.platform.common.notice.core.MessagePayload;
import com.xht.platform.common.notice.core.MessageUser;
import com.xht.platform.common.notice.enums.MessageTypeEnums;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 消息载体内容构建器
 *
 * @author xht
 **/
public final class MessagePayloadBuilder {

    /**
     * 空消息扩展信息对象
     */
    private static final MessageExtendInfo EMPTY_MESSAGE_EXTEND = new MessageExtendInfo();

    /**
     * 管理员用户对象
     */
    private static final MessageUser ADMIN_USER = new MessageUser(0L, "管理员");

    static {
        EMPTY_MESSAGE_EXTEND.setJumpUrl(null);
    }


    private MessagePayloadBuilder() {
        throw new UtilException("消息载体内容构建器 禁止实例化");
    }

    /**
     * 发件人信息
     */
    private MessageUser sendUser;

    /**
     * 收件人 信息
     */
    private final List<MessageUser> recipientUser = new ArrayList<>();

    /**
     * 消息类型：1-系统通知 2-业务提醒
     */
    private MessageTypeEnums messageType;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 消息内容（支持富文本）
     */
    private String messageContent;

    /**
     * 创建消息载体内容构建器
     *
     * @return 消息载体内容构建器
     */
    public static MessagePayloadBuilder builder() {
        return new MessagePayloadBuilder();
    }

    /**
     * 设置发送者为管理员
     *
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder senderAdmin() {
        this.sendUser = ADMIN_USER;
        return this;
    }

    /**
     * 设置发送者信息
     *
     * @param sendUser 发送者信息
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder sender(MessageUser sendUser) {
        if (checkUser(sendUser)) {
            throw new UtilException("请设置发送者信息");
        }
        this.sendUser = sendUser;
        return this;
    }

    /**
     * 设置接收者信息
     *
     * @param sendUser 接收者信息
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder recipient(MessageUser sendUser) {
        if (checkUser(sendUser)) {
            throw new UtilException("请设置接收者信息");
        }
        this.recipientUser.add(sendUser);
        return this;
    }

    /**
     * 设置接收者信息
     *
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder recipient(List<MessageUser> sendUsers) {
        sendUsers.forEach(item -> {
            if (checkUser(sendUser)) {
                throw new UtilException("请设置接收者信息");
            }
        });
        this.recipientUser.addAll(sendUsers);
        return this;
    }

    /**
     * 设置消息类型
     *
     * @param messageType 消息类型枚举
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder messageType(MessageTypeEnums messageType) {
        this.messageType = messageType;
        return this;
    }

    /**
     * 设置消息标题
     *
     * @param messageTitle 消息标题
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder messageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
        return this;
    }

    /**
     * 设置消息内容
     *
     * @param messageContent 消息内容
     * @return MessagePayloadBuilder实例，支持链式调用
     */
    public MessagePayloadBuilder messageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    /**
     * 构建消息载体内容
     *
     * @return 消息载体内容
     */
    public MessagePayload build() {
        return build(null);
    }

    /**
     * 构建消息载体内容
     *
     * @param messageExtend 消息扩展信息对象
     * @return 消息载体内容
     */
    public MessagePayload build(MessageExtendInfo messageExtend) {
        check();
        if (Objects.nonNull(messageExtend)) {
            this.messageContent = StringFormatter.format(this.messageContent, messageExtend);
        }
        return new MessagePayload(
                this.sendUser,
                this.recipientUser,
                this.messageType,
                this.messageTitle,
                this.messageContent,
                Optional.ofNullable(messageExtend).orElse(EMPTY_MESSAGE_EXTEND)
        );
    }


    /**
     * 检查参数
     */
    private void check() {
        if (checkUser(this.sendUser)) {
            throw new UtilException("请设置发送者信息");
        }
        if (this.recipientUser.isEmpty()) {
            throw new UtilException("请设置接收者信息");
        }
        if (Objects.isNull(this.messageType)) {
            throw new UtilException("请设置消息类型");
        }
        if (Objects.isNull(this.messageTitle)) {
            throw new UtilException("请设置消息标题");
        }
        if (Objects.isNull(this.messageContent)) {
            throw new UtilException("请设置消息内容");
        }
    }

    /**
     * 检查用户信息
     *
     * @param messageUser 用户信息
     * @return true-用户信息为空
     */
    private boolean checkUser(MessageUser messageUser) {
        return Objects.isNull(messageUser) || messageUser.isEmpty();
    }


}
