package com.xht.modules.admin.notice.domain.query;


import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.platform.common.notice.enums.MessageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ： 系统管理-站内信主表
 *
 * @author xht
 **/
@Data
@Schema(description = "站内信分页查询参数")
public class SysMessageQuery extends PageBasicQuery {

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

}
