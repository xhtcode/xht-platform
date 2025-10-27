package com.xht.framework.core.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 通用的删除请求类
 */
@Data
@Schema(description = "基础删除表单抽象类")
public abstract class DeleteForm extends BasicForm {

    @Serial
    private static final long serialVersionUID = 1L;

}