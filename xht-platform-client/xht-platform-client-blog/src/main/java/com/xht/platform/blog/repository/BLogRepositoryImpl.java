package com.xht.platform.blog.repository;

import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.platform.common.audit.api.IBLogClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * bLog 日志监听器
 *
 * @author xht
 **/
@Slf4j
public class BLogRepositoryImpl implements BLogRepository {

    @Resource
    private IBLogClient blogClient;

    /**
     * 保存日志
     *
     * @param bLog 日志DTO
     */
    @Override
    public void save(BLogDTO bLog) {

    }
}