package com.xht.framework.core.support.blog;

import com.xht.framework.core.support.blog.dto.BLogDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 日志事件
 *
 * @author xht
 **/
@Getter
public class BLogEvent extends ApplicationEvent {

    private final BLogDTO bLogDTO;

    public BLogEvent(BLogDTO bLogDTO) {
        super(bLogDTO);
        this.bLogDTO = bLogDTO;
    }
}
