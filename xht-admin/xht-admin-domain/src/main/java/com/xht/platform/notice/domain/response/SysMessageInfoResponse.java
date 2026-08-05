package com.xht.platform.notice.domain.response;


import com.xht.framework.common.domain.response.MetaResponse;
import  com.xht.platform.notice.enums.MessageStarEnum;
import  com.xht.platform.notice.enums.MessageStatusEnum;
import  com.xht.platform.notice.enums.MessageTopEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统管理-站内信收件人明细表
 *
 * @author xht
 */
@Data
@Schema(description = "站内信收件人明细表响应信息")
public class SysMessageInfoResponse extends MetaResponse {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 主键ID
     */
    @Schema(description = "关联主表的消息唯一编号")
    private Long messageId;

    /**
     * 主键ID
     */
    @Schema(description = "收件人ID")
    private Long recipientId;

    /**
     * 主键ID
     */
    @Schema(description = "收件人名称")
    private String recipientName;

    /**
     * 消息状态：1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）
     */
    @Schema(description = "消息状态：1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）")
    private MessageStatusEnum messageStatus;

    /**
     * 信息置顶：0-否 1-是
     */
    @Schema(description = "信息置顶：0-否 1-是")
    private MessageTopEnum messageTop;

    /**
     * 信息收藏：0-否 1-是
     */
    @Schema(description = "信息收藏：0-否 1-是")
    private MessageStarEnum messageStar;

    /**
     * 阅读时间
     */
    @Schema(description = "阅读时间")
    private LocalDateTime readTime;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private LocalDateTime removeTime;

    /**
     * 撤回时间
     */
    @Schema(description = "撤回时间")
    private LocalDateTime cancelTime;

}
