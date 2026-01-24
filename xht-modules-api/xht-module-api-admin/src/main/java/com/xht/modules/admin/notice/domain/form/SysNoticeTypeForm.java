package com.xht.modules.admin.notice.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    private Long id;

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    @NotBlank(message = "类型名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private String noticeTypeName;

    /**
     * 通知类型
     */
    @Schema(description = "通知类型")
    @NotBlank(message = "通知类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Integer noticeTypeSort;

}