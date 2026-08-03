package com.xht.workflow.flowable.utils;


import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.util.io.StringStreamSource;

import java.util.*;

public class BpmnUtils {
    private static final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    private BpmnUtils() {
    }

    public static BpmnModel getBpmnModel(String xml) {
        return bpmnXMLConverter.convertToBpmnModel(new StringStreamSource(xml), false, false);
    }

    public static byte[] getBpmnXml(BpmnModel bpmnModel) {
        return bpmnXMLConverter.convertToXML(bpmnModel);
    }

    public static StartEvent getStartEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        FlowElement startElement = process.getInitialFlowElement();
        return startElement instanceof StartEvent ? (StartEvent) startElement : getStartEvent(process.getFlowElements());
    }

    public static StartEvent getStartEvent(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof StartEvent) {
                return (StartEvent) flowElement;
            }
        }

        return null;
    }

    public static EndEvent getEndEvent(BpmnModel model) {
        Process process = model.getMainProcess();
        return getEndEvent(process.getFlowElements());
    }

    public static EndEvent getEndEvent(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof EndEvent) {
                return (EndEvent) flowElement;
            }
        }

        return null;
    }

    public static boolean isMultiInstance(BpmnModel model, String taskKey) {
        UserTask userTask = getUserTaskByKey(model, taskKey);
        return userTask != null ? userTask.hasMultiInstanceLoopCharacteristics() : false;
    }

    public static UserTask getUserTaskByKey(BpmnModel model, String taskKey) {
        Process process = model.getMainProcess();
        FlowElement flowElement = process.getFlowElement(taskKey);
        return flowElement instanceof UserTask ? (UserTask) flowElement : null;
    }

    public static List<UserTask> findNextUserTasks(FlowElement source) {
        return findNextUserTasks(source, (Set) null, (List) null);
    }

    public static List<UserTask> findNextUserTasks(FlowElement source, Set<String> hasSequenceFlow, List<UserTask> userTaskList) {
        hasSequenceFlow = (Set) Optional.ofNullable(hasSequenceFlow).orElse(new HashSet());
        userTaskList = (List) Optional.ofNullable(userTaskList).orElse(new ArrayList());
        List<SequenceFlow> sequenceFlows = getElementOutgoingFlows(source);
        if (!sequenceFlows.isEmpty()) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                if (!hasSequenceFlow.contains(sequenceFlow.getId())) {
                    hasSequenceFlow.add(sequenceFlow.getId());
                    FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
                    if (targetFlowElement instanceof UserTask) {
                        userTaskList.add((UserTask) targetFlowElement);
                    } else {
                        findNextUserTasks(targetFlowElement, hasSequenceFlow, userTaskList);
                    }
                }
            }
        }

        return userTaskList;
    }

    public static List<SequenceFlow> getElementOutgoingFlows(FlowElement source) {
        List<SequenceFlow> sequenceFlows = new ArrayList();
        if (source instanceof FlowNode) {
            sequenceFlows = ((FlowNode) source).getOutgoingFlows();
        }

        return sequenceFlows;
    }
}
