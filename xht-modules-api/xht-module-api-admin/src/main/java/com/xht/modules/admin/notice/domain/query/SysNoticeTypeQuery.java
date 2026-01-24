package com.xht.modules.admin.notice.domain.query;

import com.xht.framework.core.domain.query.BasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统管理-通知类型
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知类型")
public class SysNoticeTypeQuery extends BasicQuery {

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    private String noticeTypeName;

}