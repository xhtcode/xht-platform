package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字段信息分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "字段信息分页查询参数")
public class GenColumnInfoQueryRequest extends PageQueryRequest {

}
