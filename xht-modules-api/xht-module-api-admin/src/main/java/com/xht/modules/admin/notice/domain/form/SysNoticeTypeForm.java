package com.xht.modules.admin.notice.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import com.xht.modules.admin.notice.enums.NoticeTypeStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 系统管理-通知类型
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知类型")
public class SysNoticeTypeForm extends BasicForm {

    /**
     * 通知类型
     */
    @Schema(description = "主键ID")
    @Null(message = "唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "唯一标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Long id;

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    @NotBlank(message = "类型名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private String noticeTypeName;

    /**
     * 通知类型状态(0:未启用1:启用)
     */
    @Schema(description = "通知类型状态")
    @NotNull(message = "通知类型状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticeTypeStatusEnums noticeTypeStatus;

    /**
     * 通知排序
     */
    @Schema(description = "通知排序")
    @NotNull(message = "通知排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Integer noticeTypeSort;

}