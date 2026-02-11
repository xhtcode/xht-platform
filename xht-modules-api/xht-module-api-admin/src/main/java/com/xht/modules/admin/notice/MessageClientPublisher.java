package com.xht.modules.admin.notice;

import com.xht.modules.admin.notice.api.ISysMessageClient;
import com.xht.framework.core.support.message.IMessagePublisher;
import com.xht.framework.core.support.message.core.MessagePayload;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述 ： 消息发布者
 *
 * @author xht
 **/
@Slf4j
public class MessageClientPublisher implements IMessagePublisher {

    @Resource
    private ISysMessageClient sysMessageClient;

    /**
     * 消息推送 接口
     *
     * @param topic   主体
     * @param payload 消息载体内容
     */
    @Override
    public void publish(String topic, MessagePayload payload) {
        sysMessageClient.sendMessage(payload);
    }
}
