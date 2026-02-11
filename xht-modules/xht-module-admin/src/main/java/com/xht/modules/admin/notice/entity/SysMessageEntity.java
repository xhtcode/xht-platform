package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.framework.core.support.message.core.MessageExtendInfo;
import com.xht.framework.core.support.message.enums.MessageTypeEnums;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统管理-站内信主表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_message", autoResultMap = true)
public class SysMessageEntity extends BasicEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发件人ID（0表示系统）
     */
    @TableField(value = "sender_id")
    private Long senderId;

    /**
     * 发件人名称
     */
    @TableField(value = "sender_name")
    private String senderName;

    /**
     * 消息类型：1-系统通知 2-业务提醒
     */
    @TableField(value = "message_type")
    private MessageTypeEnums messageType;

    /**
     * 消息标题
     */
    @TableField(value = "message_title")
    private String messageTitle;

    /**
     * 撤回时间
     */
    @TableField(value = "cancel_time")
    private LocalDateTime cancelTime;

    /**
     * 消息内容（支持富文本）
     */
    @TableField(value = "message_content")
    private String messageContent;

    /**
     * 消息扩展信息（如关联订单ID、跳转链接）
     */
    @TableField(value = "message_extend", typeHandler = JacksonTypeHandler.class)
    private MessageExtendInfo messageExtend;

}