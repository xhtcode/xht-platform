package com.xht.modules.generate.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.modules.generate.domain.bo.GenCodeCoreBo;
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
public class GenCodeCoreVo implements IVO {

    private String tableName;


    private List<GenCodeCoreBo> codes;

}
