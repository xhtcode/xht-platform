package com.xht.framework.core.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 批处理删除表单ID实体类
 *
 * @author xht
 **/
@Data
@Schema(description = "批处理删除表单ID实体类")
public class DeleteBatchIdForm<T extends Serializable> extends DeleteForm {

    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(name = "ids", description = "ID列表", example = "101,102")
    private List<T> ids;

}
