package com.xht.modules.admin.notice.domain.query;


import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.modules.admin.notice.enums.MessageStarEnums;
import com.xht.modules.admin.notice.enums.MessageStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统管理-站内信收件人明细表
 *
 * @author xht
 */
@Data
@Schema(description = "站内信收件人明细表分页查询参数")
public class SysMessageInfoQuery extends PageBasicQuery {

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
     * 消息状态：1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）
     */
    @Schema(description = "消息状态：1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）")
    private MessageStatusEnums messageStatus;

    /**
     * 信息收藏：0-否 1-是
     */
    @Schema(description = "信息收藏：0-否 1-是")
    private MessageStarEnums messageStar;

}
