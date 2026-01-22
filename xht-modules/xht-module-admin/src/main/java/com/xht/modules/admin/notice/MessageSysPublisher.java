package com.xht.modules.admin.notice;

import com.xht.modules.admin.notice.service.ISysMessageService;
import com.xht.platform.common.notice.IMessagePublisher;
import com.xht.platform.common.notice.core.MessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 描述 ： 消息发布者
 *
 * @author xht
 **/
@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class MessageSysPublisher implements IMessagePublisher {

    private final ISysMessageService sysMessageService;

    /**
     * 消息推送 接口
     *
     * @param topic   主体
     * @param payload 消息载体内容
     */
    @Override
    public void publish(String topic, MessagePayload payload) {
        sysMessageService.sendMessage(payload);
    }
}
