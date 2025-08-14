package com.xht.generate.listener;

import com.xht.generate.dao.GenLogDao;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.event.GenLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 代码生成日志监听器
 *
 * @author xht
 */
@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class GenLogListener implements ApplicationListener<GenLogEvent> {

    private final GenLogDao genLogDao;

    /**
     * 处理代码生成日志事件
     *
     * @param event 代码生成日志事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(GenLogEvent event) {
        GenLogEntity genLogEntity = event.getGenLogEntity();
        genLogDao.save(genLogEntity);
    }

}
