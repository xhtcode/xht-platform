package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.modules.admin.notice.enums.MessageStarEnums;
import com.xht.modules.admin.notice.enums.MessageStatusEnums;
import com.xht.modules.admin.notice.enums.MessageTopEnums;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统管理-站内信收件人明细表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_message_info")
public class SysMessageInfoEntity extends BasicEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联主表的消息唯一编号
     */
    @TableField(value = "message_id")
    private Long messageId;

    /**
     * 收件人ID
     */
    @TableField(value = "recipient_id")
    private Long recipientId;

    /**
     * 收件人名称
     */
    @TableField(value = "recipient_name")
    private String recipientName;

    /**
     * 消息状态：1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）
     */
    @TableField(value = "message_status")
    private MessageStatusEnums messageStatus;

    /**
     * 信息置顶：0-否 1-是
     */
    @TableField(value = "message_top")
    private MessageTopEnums messageTop;

    /**
     * 信息收藏：0-否 1-是
     */
    @TableField(value = "message_star")
    private MessageStarEnums messageStar;

    /**
     * 阅读时间
     */
    @TableField(value = "read_time")
    private LocalDateTime readTime;

    /**
     * 删除时间
     */
    @TableField(value = "remove_time")
    private LocalDateTime removeTime;

    /**
     * 撤回时间
     */
    @TableField(value = "cancel_time")
    private LocalDateTime cancelTime;

}