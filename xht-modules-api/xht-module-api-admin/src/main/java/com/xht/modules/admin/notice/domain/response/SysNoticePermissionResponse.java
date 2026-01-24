package com.xht.modules.admin.notice.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.modules.admin.notice.enums.NoticePermTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统管理-通知权限
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知权限")
public class SysNoticePermissionResponse extends MetaResponse {

    /**
     * 权限ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 关联通知ID（关联sys_notice.id）
     */
    @Schema(description = "通知ID")
    private Long noticeId;

    /**
     * 权限类型(0:角色权限;1:用户权限;2:部门权限)
     */
    @Schema(description = "权限类型")
    private NoticePermTypeEnums permType;

    /**
     * 权限值（单个角色ID/用户ID/部门ID，不再存多个）
     */
    @Schema(description = "权限值")
    private String permValue;

}