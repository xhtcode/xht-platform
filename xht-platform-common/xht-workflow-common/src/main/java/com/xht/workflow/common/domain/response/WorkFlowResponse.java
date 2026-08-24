package com.xht.workflow.common.domain.response;

import com.xht.framework.common.domain.response.XhtResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 描述 ：工作流响应实体
 *
 * @author xht
 **/
@Schema(description = "工作流响应实体")
public abstract class WorkFlowResponse implements XhtResponse, Serializable {

}
