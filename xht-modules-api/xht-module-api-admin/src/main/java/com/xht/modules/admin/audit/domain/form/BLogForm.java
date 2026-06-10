package com.xht.modules.admin.audit.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统日志 表单提交参数
 *
 * @author xht
 **/
@Data
@Schema(description = "系统日志表单请求参数")
public class BLogForm extends BasicForm {
}
