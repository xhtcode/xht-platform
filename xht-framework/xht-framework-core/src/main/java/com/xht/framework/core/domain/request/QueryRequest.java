package com.xht.framework.core.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * 查询请求接口
 *
 * @author xht
 **/
@Schema(description = "查询参数")
public abstract class QueryRequest implements IRequest {


    @Serial
    private static final long serialVersionUID = 1L;
}
