package com.xht.api.system.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统日志分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "系统日志管理分页查询参数")
public class SysLogQuery extends PageBasicQuery {

    /**
     * 日志名称
     */
    @Schema(description = "日志名称")
    private String title;

    /**
     * 服务名称
     */
    @Schema(description = "服务名称")
    private String serviceName;

    /**
     * 日志描述
     */
    @Schema(description = "日志描述")
    private String description;

    /**
     * 日志状态
     */
    @Schema(description = "日志状态")
    private LogStatusEnums status;

}
