package com.xht.modules.admin.notice.domain.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.framework.core.domain.vo.IVO;
import com.xht.modules.admin.notice.domain.response.SysMessageInfoResponse;
import com.xht.platform.common.notice.core.MessageExtendInfo;
import com.xht.platform.common.notice.enums.MessageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 站内信收件人明细表VO
 * @author xht
 **/
@Data
@Schema(description = "站内信收件人明细表VO")
public class MessageInfoVo implements IVO {

    /**
     * 主键ID
     */
    @JsonIgnore
    private Long t1Id;

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

    /**
     * 消息详情响应信息
     */
    @Schema(description = "消息详情响应信息")
    private SysMessageInfoResponse response;
}
