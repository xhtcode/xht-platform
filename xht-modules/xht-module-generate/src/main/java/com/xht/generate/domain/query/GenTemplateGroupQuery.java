package com.xht.generate.domain.query;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 模板分组查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "模板分组查询参数")
public class GenTemplateGroupQuery extends PageQueryRequest {

    @Schema(name = "分组名称")
    public String groupName;
}
