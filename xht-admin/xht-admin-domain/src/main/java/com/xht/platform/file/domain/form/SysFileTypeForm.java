package com.xht.platform.file.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述： 文件预览类型配置表单（新增/修改共用）
 * <p>
 * 预览类型编码（previewType）与名称（previewName）必填；文件后缀（fileSuffix）可选，
 * 非空时表示「该后缀文件属于该预览类型」，为空时表示该预览类型的默认名称配置
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件预览类型配置表单")
public class SysFileTypeForm extends BasicForm {

    /**
     * 预览类型编码
     */
    @NotNull(message = "预览类型编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "预览类型编码(image/pdf/video/audio/text/json/other)")
    private PreviewTypeEnum previewType;

    /**
     * 预览类型名称
     */
    @NotBlank(message = "预览类型名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 30, message = "预览类型名称长度不能超过30", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "预览类型名称(图片/PDF/视频/音频/文本/JSON/其他)")
    private String previewName;

    /**
     * 文件后缀（单个，不带点，如：jpg）
     */
    @Size(max = 30, message = "文件后缀长度不能超过30", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "文件后缀，单个，不带点，如：jpg")
    private String fileSuffix;

    /**
     * 预览来源
     */
    @Schema(description = "预览来源 original/convert")
    private PreviewSourceEnum previewSource;

    /**
     * 转换目标格式
     */
    @Size(max = 20, message = "转换目标格式长度不能超过20", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "转换目标格式，如 pdf")
    private String convertTarget;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Integer enabled;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

}
