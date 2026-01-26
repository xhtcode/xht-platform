package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.modules.admin.notice.enums.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统管理-通知详情
 *
 * @author xht
 */
@Data
@TableName(value = "sys_notice")
public class SysNoticeEntity extends BasicEntity {

    /**
     * 通知ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 通知类型（如：系统公告、活动通知等）
     */
    @TableField(value = "notice_type_id")
    private Long noticeTypeId;

    /**
     * 通知标题
     */
    @TableField(value = "notice_title")
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @TableField(value = "notice_summary")
    private String noticeSummary;

    /**
     * 通知内容
     */
    @TableField(value = "notice_content")
    private String noticeContent;

    /**
     * 通知状态(0:未发布;1:已发布;2:已下架;3:已过期)
     */
    @TableField(value = "notice_status")
    private NoticeStatusEnums noticeStatus;

    /**
     * 通知排序（值越大越靠前）
     */
    @TableField(value = "notice_order")
    private Integer noticeOrder;

    /**
     * 是否置顶(0:否;1:是)
     */
    @TableField(value = "notice_top")
    private NoticeTopEnums noticeTop;

    /**
     * 是否全部可见(0:否(指定范围);1:是(所有用户可见))
     */
    @TableField(value = "notice_all_visible")
    private NoticeAllVisibleEnums noticeAllVisible;

    /**
     * 是否定时发布(0:否(立即发布);1:是(按发布时间生效))
     */
    @TableField(value = "notice_timed_publish")
    private NoticeTimedPublishEnums noticeTimedPublish;

    /**
     * 发布时间（正式生效时间）
     */
    @TableField(value = "notice_publish_time")
    private LocalDateTime noticePublishTime;

    /**
     * 过期时间（自动失效，到期后不再展示）
     */
    @TableField(value = "notice_expire_time")
    private Date noticeExpireTime;

    /**
     * 下架时间（手动操作下架的时间）
     */
    @TableField(value = "notice_offline_time")
    private Date noticeOfflineTime;

    /**
     * 跳转类型(0:无跳转;1:内部页面;2:外部链接)
     */
    @TableField(value = "notice_jump_type")
    private NoticeJumpTypeEnums noticeJumpType;

    /**
     * 跳转地址（内部页面路径/外部URL）
     */
    @TableField(value = "notice_jump_url")
    private String noticeJumpUrl;

    /**
     * 已读人数
     */
    @TableField(value = "notice_read_count")
    private Integer noticeReadCount;

    /**
     * 点击次数
     */
    @TableField(value = "notice_click_count")
    private Integer noticeClickCount;

    /**
     * 备注（通知背景、修改说明等）
     */
    @TableField(value = "notice_remark")
    private String noticeRemark;

}