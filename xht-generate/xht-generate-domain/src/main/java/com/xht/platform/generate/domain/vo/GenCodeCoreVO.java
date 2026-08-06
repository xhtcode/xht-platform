package com.xht.platform.generate.domain.vo;

import com.xht.framework.common.domain.vo.XhtVO;
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

    private String tableName;


    private List<?> codes;

}
