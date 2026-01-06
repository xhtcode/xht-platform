package com.xht.modules.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

/**
 * 表信息表单请求参数
 * @author xht
 **/
@Data
@Schema(description = "表信息表单请求参数")
public class TableColumnForm {

    @Valid
    @Schema(description = "表信息")
    private GenTableInfoForm tableInfo;

    @Valid
    @Schema(description = "字段信息")
    private List<GenColumnInfoForm> columnInfos;

    @Valid
    @Schema(description = "查询字段信息")
    private List<GenTableColumnQueryForm> queryColumns;

}
