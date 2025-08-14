package com.xht.generate.event;

import com.xht.generate.domain.entity.GenLogEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 代码生成日志事件
 *
 * @author xht
 */
@Getter
public class GenLogEvent extends ApplicationEvent {

    private final GenLogEntity genLogEntity;

    /**
     * 构造方法
     *
     * @param genLogEntity 代码生成日志实体
     */
    public GenLogEvent(GenLogEntity genLogEntity) {
        super(genLogEntity);
        this.genLogEntity = genLogEntity;
    }

}
