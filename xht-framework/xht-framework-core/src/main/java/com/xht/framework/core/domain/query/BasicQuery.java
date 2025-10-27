package com.xht.framework.core.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询请求接口
 *
 * @author xht
 **/
@Schema(description = "查询参数")
public abstract class BasicQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
