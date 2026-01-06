package com.xht.framework.mybatis.blog.listener;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.core.support.blog.BLogEvent;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.mybatis.blog.mapper.BLogMapper;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * bLog 日志监听器
 *
 * @author xht
 **/
@Slf4j
public class BLogApplicationListener implements ApplicationListener<BLogEvent> {

    @Resource
    private BLogMapper bLogMapper;

    /**
     * 监听bLog事件
     *
     * @param bLogEvent bLog事件对象
     */
    @Override
    public void onApplicationEvent(@NonNull BLogEvent bLogEvent) {
        BLogDTO bLogDTO = bLogEvent.getBLogDTO();
        bLogMapper.saveLog(IdUtil.getSnowflakeNextId(), bLogDTO);
    }

}
