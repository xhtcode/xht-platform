package com.xht.framework.log.repository;

import com.xht.framework.core.repository.IRepository;
import com.xht.framework.log.dto.BLogDTO;

/**
 * blog 日志Repository
 *
 * @author xht
 **/
@FunctionalInterface
public interface BLogRepository extends IRepository {

    /**
     * 保存日志
     *
     * @param bLog 日志DTO
     */
    void save(BLogDTO bLog);

}
