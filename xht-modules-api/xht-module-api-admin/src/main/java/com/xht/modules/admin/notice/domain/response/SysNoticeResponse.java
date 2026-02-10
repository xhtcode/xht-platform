package com.xht.modules.admin.notice.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.modules.admin.notice.enums.*;
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
public class SysNoticeResponse extends MetaResponse {

    /**
     * 通知ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 通知类型（如：系统公告、活动通知等）
     */
    @Schema(description = "通知类型")
    private Long noticeTypeId;

    /**
     * 通知类型名称
     */
    @Schema(description = "通知类型名称")
    private String noticeTypeName;

    /**
     * 通知标题
     */
    @Schema(description = "通知标题")
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @Schema(description = "通知摘要")
    private String noticeSummary;

    /**
     * 通知内容
     */
    @Schema(description = "通知内容")
    private String noticeContent;

    /**
     * 通知状态(0:未发布;1:已发布;2:已下架;3:已过期)
     */
    @Schema(description = "通知状态")
    private NoticeStatusEnums noticeStatus;

    /**
     * 通知排序（值越大越靠前）
     */
    @Schema(description = "通知排序")
    private Integer noticeOrder;

    /**
     * 是否置顶(0:否;1:是)
     */
    @Schema(description = "是否置顶")
    private NoticeTopEnums noticeTop;

    /**
     * 是否定时发布(0:否(立即发布);1:是(按发布时间生效))
     */
    @Schema(description = "是否定时发布(0:否(立即发布);1:是(按发布时间生效))")
    private NoticeTimedPublishEnums noticeTimedPublish;

    /**
     * 发布时间（正式生效时间）
     */
    @Schema(description = "发布时间")
    private LocalDateTime noticePublishTime;

    /**
     * 过期时间（自动失效，到期后不再展示）
     */
    @Schema(description = "过期时间")
    private LocalDateTime noticeExpireTime;

    /**
     * 下架时间（手动操作下架的时间）
     */
    @Schema(description = "下架时间")
    private LocalDateTime noticeOfflineTime;

    /**
     * 跳转类型(0:无跳转;1:内部页面;2:外部链接)
     */
    @Schema(description = "跳转类型")
    private NoticeJumpTypeEnums noticeJumpType;

    /**
     * 跳转地址（内部页面路径/外部URL）
     */
    @Schema(description = "跳转地址")
    private String noticeJumpUrl;

    /**
     * 已读人数
     */
    @Schema(description = "已读人数")
    private Integer noticeReadCount;

    /**
     * 点击次数
     */
    @Schema(description = "点击次数")
    private Integer noticeClickCount;

    /**
     * 备注（通知背景、修改说明等）
     */
    @Schema(description = "备注")
    private String noticeRemark;

}