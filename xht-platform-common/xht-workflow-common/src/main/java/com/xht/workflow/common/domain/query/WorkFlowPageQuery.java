package com.xht.workflow.common.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述： 工作流分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "工作流分页查询参数")
public class WorkFlowPageQuery extends PageBasicQuery {
}
