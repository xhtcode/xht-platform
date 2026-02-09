package com.xht.modules.admin.notice.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import com.xht.modules.admin.notice.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统管理-通知详情
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知详情")
public class SysNoticeForm extends BasicForm {

    /**
     * 通知ID
     */
    @Schema(description = "主键ID")
    @Null(message = "唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "唯一标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Long id;

    /**
     * 通知类型（如：系统公告、活动通知等）
     */
    @Schema(description = "通知类型")
    @NotNull(message = "通知类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Long noticeTypeId;

    /**
     * 通知标题
     */
    @Schema(description = "通知标题")
    @NotEmpty(message = "通知标题参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @Schema(description = "通知摘要")
    @NotEmpty(message = "通知摘要参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private String noticeSummary;

    /**
     * 通知内容
     */
    @Schema(description = "通知内容")
    @NotEmpty(message = "通知内容参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private String noticeContent;

    /**
     * 通知状态(0:未发布;1:已发布;2:已下架;3:已过期)
     */
    @Schema(description = "通知状态")
    @NotNull(message = "通知状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticeStatusEnums noticeStatus;

    /**
     * 通知排序（值越大越靠前）
     */
    @Schema(description = "通知排序")
    @NotNull(message = "通知排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Integer noticeOrder;

    /**
     * 是否置顶(0:否;1:是)
     */
    @Schema(description = "是否置顶")
    @NotNull(message = "是否置顶参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticeTopEnums noticeTop;

    /**
     * 是否全部可见(0:否(指定范围);1:是(所有用户可见))
     */
    @Schema(description = "是否全部可见(0:否(指定范围);1:是(所有用户可见))")
    @NotNull(message = "是否全部可见参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticeAllVisibleEnums noticeAllVisible;

    /**
     * 是否定时发布(0:否(立即发布);1:是(按发布时间生效))
     */
    @Schema(description = "是否定时发布(0:否(立即发布);1:是(按发布时间生效))")
    @NotNull(message = "是否定时发布参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticeTimedPublishEnums noticeTimedPublish;

    /**
     * 发布时间（正式生效时间）
     */
    @Schema(description = "发布时间")
    private LocalDateTime noticePublishTime;

    /**
     * 跳转类型(0:无跳转;1:内部页面;2:外部链接)
     */
    @Schema(description = "跳转类型")
    @NotNull(message = "跳转类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticeJumpTypeEnums noticeJumpType;

    /**
     * 跳转地址（内部页面路径/外部URL）
     */
    @Schema(description = "跳转地址")
    private String noticeJumpUrl;

    /**
     * 备注（通知背景、修改说明等）
     */
    @Schema(description = "备注")
    private String noticeRemark;

    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    private List<SysNoticeAttachmentForm> attachmentList;

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<SysNoticePermissionForm> permissionList;

}