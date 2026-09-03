package com.xht.workflow.common.constant;

/**
 * 描述： BPMN 常量 类
 *
 * @author xht
 **/
public interface BpmnConstant {

    /**
     * bpmn文件后缀
     */
    String BPMN_FILE_SUFFIX = ".bpmn20.xml";

    /**
     * 流程图图片类型
     */
    String PROCESS_DIAGRAM_IMAGE_TYPE = "png";

    /**
     * 流程图活动节点字体
     */
    String PROCESS_DIAGRAM_ACTIVITY_FONT = "宋体";

    /**
     * 流程图连线标签字体
     */
    String PROCESS_DIAGRAM_LABEL_FONT = "微软雅黑";

    /**
     * 流程图注释字体
     */
    String PROCESS_DIAGRAM_ANNOTATION_FONT = "黑体";

    /**
     * 流程图缩放比例
     */
    double PROCESS_DIAGRAM_SCALE_FACTOR = 1.0D;

    /**
     * 历史活动中连线记录的活动类型
     */
    String SEQUENCE_FLOW_ACTIVITY_TYPE = "sequenceFlow";

}
