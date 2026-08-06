package com.xht.platform.generate.domain.vo;

import com.xht.framework.common.domain.vo.XhtVO;
import com.xht.platform.generate.domain.response.GenCodeCoreResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 代码生成核心业务对象
 *
 * @author xht
 **/
@Data
@Schema(description = "代码生成核心业务对象")
public class GenCodeCoreVO implements XhtVO {

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

    /**
     * 代码列表
     */
    @Schema(description = "代码列表")
    private List<GenCodeCoreResponse> codes;

}
