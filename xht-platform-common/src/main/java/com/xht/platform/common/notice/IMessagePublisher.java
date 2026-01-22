package com.xht.platform.common.notice;

import com.xht.platform.common.notice.core.MessagePayload;

/**
 * 消息发布者
 *
 * @author xht
 **/
public interface IMessagePublisher {

    /**
     * 消息推送 接口
     *
     * @param topic   主体
     * @param payload 消息载体内容
     */
    void publish(String topic, MessagePayload payload);

}
