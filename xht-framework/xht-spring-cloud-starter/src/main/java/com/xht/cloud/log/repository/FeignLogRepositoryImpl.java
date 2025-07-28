package com.xht.cloud.log.repository;

import com.xht.cloud.log.feign.RemoteLogClientService;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * 日志存储
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class FeignLogRepositoryImpl implements LogRepository {

    private final RemoteLogClientService remoteLogClientService;

    /**
     * 存储日志
     *
     * @param dto {@link LogDTO}
     */
    @Async
    @Override
    public void save(LogDTO dto) {
        remoteLogClientService.saveLog(dto);
    }
}
