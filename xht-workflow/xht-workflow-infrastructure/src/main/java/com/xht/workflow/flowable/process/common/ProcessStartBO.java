package com.xht.workflow.flowable.process.common;

import com.xht.workflow.common.domain.enums.ProcStartTypeEnum;
import com.xht.workflow.flowable.common.bo.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 描述： 流程启动参数
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessStartBO extends BpmnBO {

    /**
     * 流程启动类型
     */
    private ProcStartTypeEnum procStartType;

    /**
     * 流程启动值
     */
    private String procStartValue;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 流程启动意见
     */
    private String comment;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

}
