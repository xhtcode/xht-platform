package com.xht.workflow.common.domain.form;

import com.xht.framework.common.domain.XhtRequest;
import com.xht.framework.common.domain.form.XhtForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 描述： 工作流表单
 *
 * @author xht
 **/
@Data
@Schema(description = "基础表单抽象类")
public abstract class WorkFlowForm implements XhtForm, XhtRequest {

    @Serial
    private static final long serialVersionUID = 1L;
}
