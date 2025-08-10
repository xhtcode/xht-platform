package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 数据源响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "数据源响应信息")
public class GenDataSourceResponse extends BasicResponse {

}
