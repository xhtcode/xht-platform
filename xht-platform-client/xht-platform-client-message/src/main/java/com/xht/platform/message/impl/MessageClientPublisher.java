package com.xht.platform.message.impl;

import com.xht.framework.common.domain.R;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.ROptional;
import com.xht.platform.common.message.IMessagePublisher;
import com.xht.platform.common.message.core.MessagePayload;
import com.xht.platform.common.message.api.ISysMessageClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述：消息发布者
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
        R<Void> voidR = sysMessageClient.sendMessage(payload);
        if (!ROptional.of(voidR).isSuccess()) {
            throw new BusinessException(BusinessErrorCode.MESSAGE_PUSH_FAIL);
        }
    }

}
