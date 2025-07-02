package com.xht.boot.log.repository;

import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.log.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志存储
 *
 * @author xht
 **/
@Slf4j
public class DefaultLogRepositoryImpl implements LogRepository {

    /**
     * 存储日志
     *
     * @param dto {@link LogDTO}
     */
    @Override
    public void save(LogDTO dto) {
        log.info("默认日志存储打印{}", dto.toString());
    }
}
