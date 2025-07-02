package com.xht.framework.log.repository;

import com.xht.framework.log.domain.dto.LogDTO;

/**
 * 日志 Repository
 *
 * @author xht
 **/
public interface LogRepository {

    /**
     * 存储日志
     *
     * @param dto {@link LogDTO}
     */
    void save(LogDTO dto);
}
