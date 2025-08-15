package com.xht.generate.domain;

import com.xht.generate.constant.enums.GenStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *  字段扩展配置
 * @author xht
 **/
@Data
@Schema(description = "字段扩展配置")
public class ColumnExtConfig {

    /**
     * 实体属性 0：否 1：是
     */
    @Schema(description = "实体属性")
    private GenStatusEnums entity;

    /**
     * 表单项 0：否 1：是
     */
    @Schema(description ="表单项 0：否 1：是")
    private GenStatusEnums formItem;

    /**
     * 表单必填 0：否 1：是
     */
    @Schema(description ="表单必填 0：否 1：是")
    private GenStatusEnums formRequired;

    /**
     * 表单类型
     */
    @Schema(description ="表单类型")
    private String formType;

    /**
     * 表单效验 0：否 1：是
     */
    @Schema(description ="表单效验")
    private GenStatusEnums formValidator;

    /**
     * 列表项 0：否 1：是
     */
    @Schema(description ="列表项 0：否 1：是")
    private GenStatusEnums list;

    /**
     * 列表排序 0：否 1：是
     */
    @Schema(description ="列表排序 0：否 1：是")
    private GenStatusEnums listSort;

    /**
     * 查询项 0：否 1：是
     */
    @Schema(description ="查询项 0：否 1：是")
    private GenStatusEnums queryItem;

    /**
     * 查询方式
     */
    @Schema(description ="查询方式")
    private String queryType;

    /**
     * 查询表单类型
     */
    @Schema(description ="查询表单类型")
    private String queryFormType;

}