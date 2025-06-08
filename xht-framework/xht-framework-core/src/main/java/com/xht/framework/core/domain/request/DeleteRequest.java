package com.xht.framework.core.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用的删除请求类
 */
@Data
@Schema(description = "通用的删除请求类")
public class DeleteRequest implements IRequest {

    @Serial
    private static final long serialVersionUID = 1L;


}