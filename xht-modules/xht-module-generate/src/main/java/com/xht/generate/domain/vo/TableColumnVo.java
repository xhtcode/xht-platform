package com.xht.generate.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.generate.domain.response.GenTableColumnQueryResp;
import com.xht.generate.domain.response.GenTableColumnResp;
import com.xht.generate.domain.response.GenTableResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 表字段信息
 *
 * @author xht
 **/
@Data
@Schema(description = "表字段信息")
public class TableColumnVo implements IVO {

    @Schema(description = "表信息")
    private GenTableResp tableInfo;

    @Schema(description = "字段信息")
    private List<GenTableColumnResp> columnInfos;

    @Schema(description = "查询字段信息")
    private List<GenTableColumnQueryResp> queryColumns;

}
