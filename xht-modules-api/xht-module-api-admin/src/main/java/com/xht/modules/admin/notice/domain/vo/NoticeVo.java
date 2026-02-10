package com.xht.modules.admin.notice.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.modules.admin.notice.domain.response.SysNoticeAttachmentResponse;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 站内信详情VO
 * @author xht
 **/
@Data
@Schema(description = "站内信详情VO")
public class NoticeVo implements IVO {

    @Schema(description = "通知详情")
    private SysNoticeResponse notice;

    @Schema(description = "通知附件")
    private List<SysNoticeAttachmentResponse> attachments;

}
