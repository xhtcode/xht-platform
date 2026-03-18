package com.xht.framework.log.repository.impl;

import com.xht.framework.core.jackson.JsonUtils;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.log.repository.BLogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Json日志Repository实现类
 *
 * @author xht
 **/
@Slf4j
public class JsonBLogRepository implements BLogRepository {

    /**
     * 保存日志
     *
     * @param bLogDTO 日志DTO
     */
    @Override
    public void save(BLogDTO bLogDTO) {
        log.debug("日志保存成功:{}", JsonUtils.toJsonString(bLogDTO));
    }
}
