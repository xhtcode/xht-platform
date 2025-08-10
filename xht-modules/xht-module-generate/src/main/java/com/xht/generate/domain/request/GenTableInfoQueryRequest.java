package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 表信息分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "表信息分页查询参数")
public class GenTableInfoQueryRequest extends PageQueryRequest {

}
