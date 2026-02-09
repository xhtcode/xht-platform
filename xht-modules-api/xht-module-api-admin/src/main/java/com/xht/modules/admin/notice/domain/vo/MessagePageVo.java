package com.xht.modules.admin.notice.domain.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.modules.admin.notice.domain.response.SysMessageInfoResponse;
import com.xht.platform.common.notice.core.MessageExtendInfo;
import com.xht.platform.common.notice.enums.MessageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 站内信详情VO
 *
 * @author xht
 **/
@Data
@Schema(description = "站内信详情VO")
public class MessagePageVo extends PageResponse<SysMessageInfoResponse> {

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
