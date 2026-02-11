package com.xht.modules.admin.notice.domain.response;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.framework.core.support.message.core.MessageExtendInfo;
import com.xht.framework.core.support.message.enums.MessageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述 ： 系统管理-站内信主表
 *
 * @author xht
 **/
@Data
@Schema(description = "站内信响应信息")
public class SysMessageResponse extends MetaResponse {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 发件人ID
     */
    @Schema(description = "发件人ID（0表示系统）")
    private Long senderId;

    /**
     * 发件人名称
     */
    @Schema(description = "发件人名称")
    private String senderName;

    /**
     * 消息类型：1-系统通知 2-业务提醒
     */
    @Schema(description = "消息类型：1-系统通知 2-业务提醒")
    private MessageTypeEnums messageType;

    /**
     * 消息扩展信息消息标题
     */
    @Schema(description = "消息标题")
    private String messageTitle;

    /**
     * 撤回时间
     */
    @Schema(description = "撤回时间")
    private LocalDateTime cancelTime;

    /**
     * 消息内容 （支持富文本）
     */
    @Schema(description = "消息内容")
    private String messageContent;

    /**
     * 消息扩展信息
     */
    @JsonAlias("extend")
    @Schema(description = "消息扩展信息（如关联订单ID、跳转链接）")
    private MessageExtendInfo messageExtend;

}
