package com.xht.platform.notice.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import  com.xht.platform.notice.enums.NoticeTypeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private NoticeTypeStatusEnum noticeTypeStatus;

    /**
     * 通知排序
     */
    @Schema(description = "通知排序")
    @NotNull(message = "通知排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Integer noticeTypeSort;

}