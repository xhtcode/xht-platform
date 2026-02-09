package com.xht.modules.admin.notice.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.modules.admin.notice.enums.NoticeStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统管理-通知详情
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知详情")
public class SysNoticeQuery extends PageBasicQuery {

    /**
     * 通知类型（如：系统公告、活动通知等）
     */
    @Schema(description = "通知类型")
    private Long noticeTypeId;

    /**
     * 通知标题
     */
    @Schema(description = "通知标题")
    private String noticeTitle;

    /**
     * 通知状态(0:未发布;1:已发布;2:已下架;3:已过期)
     */
    @Schema(description = "通知状态")
    private NoticeStatusEnums noticeStatus;

    /**
     * 发布开始时间
     */
    @Schema(description = "发布开始时间")
    private LocalDateTime noticePublishTimeStart;

    /**
     * 发布结束时间
     */
    @Schema(description = "发布结束时间")
    private LocalDateTime noticePublishTimeEnd;

    /**
     * 过期开始时间
     */
    @Schema(description = "过期开始时间")
    private LocalDateTime noticeExpireTimeStart;

    /**
     * 过期结束时间
     */
    @Schema(description = "过期结束时间")
    private LocalDateTime noticeExpireTimeEnd;

    /**
     * 下架开始时间
     */
    @Schema(description = "下架开始时间")
    private LocalDateTime noticeOfflineTimeStart;

    /**
     * 下架结束时间
     */
    @Schema(description = "下架结束时间")
    private LocalDateTime noticeOfflineTimeEnd;

}