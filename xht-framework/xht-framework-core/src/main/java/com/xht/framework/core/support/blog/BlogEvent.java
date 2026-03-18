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
public class BlogEvent extends ApplicationEvent {

    private final BLogDTO bLogDTO;

    public BlogEvent(BLogDTO source) {
        super(source);
        this.bLogDTO = source;
    }

}
