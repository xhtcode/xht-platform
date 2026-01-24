package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.DeleteEntity;
import com.xht.modules.admin.notice.enums.NoticeOperateTypeEnums;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统管理-用户操作记录(防重复统计)
 *
 * @author xht
 */
@Data
@TableName(value = "sys_notice_user_operate")
public class SysNoticeUserOperateEntity extends DeleteEntity {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知ID
     */
    @TableField(value = "notice_id")
    private Long noticeId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 操作类型(1:阅读;2:点击)
     */
    @TableField(value = "operate_type")
    private NoticeOperateTypeEnums operateType;

    /**
     * 操作时间
     */
    @TableField(value = "operate_time")
    private LocalDateTime operateTime;

}