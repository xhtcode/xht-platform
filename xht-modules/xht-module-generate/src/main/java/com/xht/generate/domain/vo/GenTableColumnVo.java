package com.xht.generate.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.generate.domain.response.GenTableColumnResponse;
import com.xht.generate.domain.response.GenTableResponse;
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
public class GenTableColumnVo extends GenTableResponse implements IVO {


    @Schema(description = "字段信息")
    private List<GenTableColumnResponse> column;
}
