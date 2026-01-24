package com.xht.modules.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 系统管理-通知附件
 *
 * @author xht
 */
@Data
@TableName(value = "sys_notice_attachment")
public class SysNoticeAttachmentEntity extends BasicEntity {

    /**
     * 附件ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联通知ID（关联sys_notice.id）
     */
    @TableField(value = "notice_id")
    private Long noticeId;

    /**
     * 附件原始名称（文件上传时的原名，如：20260122公告.pdf）
     */
    @TableField(value = "attachment_name")
    private String attachmentName;

    /**
     * 附件显示名称（自定义展示名，为空则显示原始名称）
     */
    @TableField(value = "attachment_show_name")
    private String attachmentShowName;

    /**
     * 附件存储路径（服务器绝对路径/OSS地址）
     */
    @TableField(value = "attachment_path")
    private String attachmentPath;

    /**
     * 附件大小（单位：字节）
     */
    @TableField(value = "attachment_size")
    private Long attachmentSize;

    /**
     * 附件类型（如：pdf、doc、jpg、zip等）
     */
    @TableField(value = "attachment_type")
    private String attachmentType;

    /**
     * 附件排序（值越大越靠前）
     */
    @TableField(value = "attachment_order")
    private Integer attachmentOrder;

}