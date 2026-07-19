package com.xht.framework.common.domain.form;

import com.xht.framework.common.domain.XhtRequest;
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
public class DeleteBatchIdForm<T extends Serializable> implements XhtForm, XhtRequest {

    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(name = "ids", description = "ID列表")
    private List<T> ids;

}
