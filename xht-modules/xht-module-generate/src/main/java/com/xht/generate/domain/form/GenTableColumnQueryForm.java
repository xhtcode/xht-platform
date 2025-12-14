package com.xht.generate.domain.form;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xht
 **/
@Data
@Schema(description = "代码生成器-查询条件-表单请求参数")
public class GenTableColumnQueryForm extends BasicForm {

    /**
     * 表id
     */
    @Schema(description = "表id")
    private Long tableId;

    /**
     * 表名称(冗余字段)
     */
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 字段id
     */
    @Schema(description = "字段id")
    private Long columnId;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;

    /**
     * 表单输入长度
     */
    @Schema(description = "表单输入长度")
    private Integer fromLength;

    /**
     * 查询类型（如等于、不等于、大于、小于等）
     */
    @Schema(description = "查询类型")
    private String queryType;

    /**
     * 条件标签（显示用的文本）
     */
    @Schema(description = "条件标签")
    private String conditionLabel;

    /**
     * 字段值（字段命名）
     */
    @Schema(description = "字段值（字段命名）")
    private String conditionValue;

    /**
     * 表单组件
     */
    @Schema(description = "表单组件")
    private String fromComponent;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字段排序
     */
    @Schema(description = "字段排序")
    private Integer sortOrder;
}