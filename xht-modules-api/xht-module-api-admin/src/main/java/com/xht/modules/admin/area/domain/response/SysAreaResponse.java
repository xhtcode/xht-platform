package com.xht.modules.admin.area.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 */
@Data
@Schema(description = "行政区划")
public class SysAreaResponse extends BasicResponse {

    /**
     * 区划编码
     */
    @Schema(description = "区划编码")
    private String areaCode;

    /**
     * 上级区划编码
     */
    @Schema(description = "上级区划编码")
    private String parentAreaCode;

    /**
     * 区划名称
     */
    @Schema(description = "区划名称")
    private String areaName;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码")
    private String areaPostCode;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private String areaLongitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private String areaLatitude;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer areaSort;

}