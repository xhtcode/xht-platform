package com.xht.framework.core.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 描述 ：通用修改请求实体
 *
 * @author xht
 **/
@Data
@Schema(description = "通用修改请求实体")
public abstract class FormRequest implements IRequest {

    @Serial
    private static final long serialVersionUID = 1L;


}
