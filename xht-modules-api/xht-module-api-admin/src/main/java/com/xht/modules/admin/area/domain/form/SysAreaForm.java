package com.xht.modules.admin.area.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
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
     * 主键
     */
    @Null(message = "主键必须为空", groups = {Groups.Create.class})
    @NotNull(message = "主键参数不合法", groups = {Groups.Update.class})
    @Positive(message = "主键参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "主键")
    private Long id;

    /**
     * 上级
     */
    @NotNull(message = "上级参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "上级")
    private Long parentId;

    /**
     * 区划编码
     */
    @NotEmpty(message = "区划编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "区划编码")
    private String areaCode;

    /**
     * 区划名称
     */
    @NotEmpty(message = "区划名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
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
    @NotEmpty(message = "经度参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "经度")
    private String areaLongitude;

    /**
     * 纬度
     */
    @NotEmpty(message = "纬度参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "纬度")
    private String areaLatitude;

    /**
     * 排序
     */
    @NotNull(message = "排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "排序")
    private Integer areaSort;


}