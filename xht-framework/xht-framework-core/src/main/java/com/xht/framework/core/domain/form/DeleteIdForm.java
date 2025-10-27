package com.xht.framework.core.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除表单ID实体类
 *
 * @author xht
 **/
@Data
@Schema(description = "删除表单ID实体类")
public class DeleteIdForm<T extends Serializable> extends DeleteForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 删除记录的唯一标识符
     */
    @Schema(name = "id", description = "ID", example = "101")
    private T id;

}
