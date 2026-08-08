package com.xht.framework.log.repository;

import com.xht.framework.core.repository.IRepository;

import java.io.Serializable;

/**
 * blog 日志Repository
 *
 * @author xht
 **/
@FunctionalInterface
public interface BLogRepository<T> extends IRepository {

    /**
     * 保存日志
     *
     * @param bLog 日志DTO
     */
    void save(T bLog);

}
