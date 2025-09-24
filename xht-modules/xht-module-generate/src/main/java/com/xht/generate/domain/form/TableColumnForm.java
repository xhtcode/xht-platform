package com.xht.generate.domain.form;

import com.xht.generate.domain.response.GenTableColumnQueryResponse;
import com.xht.generate.domain.response.GenTableColumnResponse;
import com.xht.generate.domain.response.GenTableResponse;
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
    private GenTableInfoFormRequest tableInfo;

    @Valid
    @Schema(description = "字段信息")
    private List<GenColumnInfoFormRequest> columnInfos;

    @Valid
    @Schema(description = "查询字段信息")
    private List<GenTableColumnQueryFormRequest> queryColumns;

}
