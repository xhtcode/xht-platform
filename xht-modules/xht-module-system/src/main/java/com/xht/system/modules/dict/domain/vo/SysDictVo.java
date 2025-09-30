package com.xht.system.modules.dict.domain.vo;

import com.xht.system.modules.dict.domain.response.SysDictItemResp;
import com.xht.system.modules.dict.domain.response.SysDictResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 字典项视图对象响应信息
 */
@Data
@Schema(description = "字典项视图对象响应信息")
public class SysDictVo extends SysDictResp {

    @Schema(description = "字典项列表")
    private List<SysDictItemResp> items;

}