package com.xht.modules.admin.notice.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.modules.admin.notice.enums.NoticeTypeStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统管理-通知类型
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知类型")
public class SysNoticeTypeResponse extends MetaResponse {

    /**
     * 通知类型
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    private String noticeTypeName;

    /**
     * 通知类型状态(0:未启用1:启用)
     */
    @Schema(description = "通知类型状态")
    private NoticeTypeStatusEnums noticeTypeStatus;

    /**
     * 通知排序
     */
    @Schema(description = "通知排序")
    private Integer noticeTypeSort;

}