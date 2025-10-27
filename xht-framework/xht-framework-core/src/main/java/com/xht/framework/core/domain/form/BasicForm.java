package com.xht.framework.core.domain.form;

import com.xht.framework.core.domain.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 基础表单抽象类，用于定义请求表单的基本结构和序列化版本控制
 *
 * @author xht
 **/
@Data
@Schema(description = "基础表单抽象类")
public abstract class BasicForm implements IRequest {

    @Serial
    private static final long serialVersionUID = 1L;

}
