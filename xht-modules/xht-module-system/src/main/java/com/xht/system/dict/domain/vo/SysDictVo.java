package com.xht.system.dict.domain.vo;

import com.xht.system.dict.domain.response.SysDictItemResponse;
import com.xht.system.dict.domain.response.SysDictResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 字典项视图对象
 */
@Data
@Schema(description = "字典项视图对象")
public class SysDictVo extends SysDictResponse {

    @Schema(description = "字典项列表")
    private List<SysDictItemResponse> items;

}