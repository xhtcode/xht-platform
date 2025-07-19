package com.xht.boot.log.repository;

import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.log.repository.LogRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 日志存储
 *
 * @author xht
 **/
@Slf4j
public class JdbcLogRepositoryImpl implements LogRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

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
