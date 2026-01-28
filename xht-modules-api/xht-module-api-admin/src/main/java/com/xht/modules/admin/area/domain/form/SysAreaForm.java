package com.xht.modules.admin.area.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 系统管理 - 行政区划
 *
 * @author xht
 */
@Data
@Schema(description = "行政区划")
public class SysAreaForm extends BasicForm {

    /**
     * 区划编码
     */
    @Schema(description = "区划编码")
    @NotEmpty(message = "区划编码不能为空")
    private String areaCode;

    /**
     * 上级区划编码
     */
    @Schema(description = "上级区划编码")
    @NotEmpty(message = "上级区划编码不能为空")
    private String parentAreaCode;

    /**
     * 区划名称
     */
    @Schema(description = "区划名称")
    @NotEmpty(message = "区划名称不能为空")
    private String areaName;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码")
    @NotEmpty(message = "邮政编码不能为空")
    private String areaPostCode;

    /**
     * 经度
     */
    @Schema(description = "经度")
    @NotEmpty(message = "经度不能为空")
    private String areaLongitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    @NotEmpty(message = "纬度不能为空")
    private String areaLatitude;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer areaSort;

}