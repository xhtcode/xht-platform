package com.xht.modules.admin.notice.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统管理-通知附件
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知附件")
public class SysNoticeAttachmentResponse extends MetaResponse {

    /**
     * 附件ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 关联通知ID
     */
    @Schema(description = "通知ID")
    private Long noticeId;

    /**
     * 附件原始名称（文件上传时的原名，如：20260122公告.pdf）
     */
    @Schema(description = "附件原始名称")
    private String attachmentName;

    /**
     * 附件显示名称（自定义展示名，为空则显示原始名称）
     */
    @Schema(description = "附件显示名称")
    private String attachmentShowName;

    /**
     * 附件存储路径（服务器绝对路径/OSS地址）
     */
    @Schema(description = "附件存储路径")
    private String attachmentPath;

    /**
     * 附件大小（单位：字节）
     */
    @Schema(description = "附件大小")
    private Long attachmentSize;

    /**
     * 附件类型（如：pdf、doc、jpg、zip等）
     */
    @Schema(description = "附件类型")
    private String attachmentType;

    /**
     * 附件排序（值越大越靠前）
     */
    @Schema(description = "附件排序")
    private Integer attachmentOrder;

}