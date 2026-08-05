package com.xht.platform.audit;

import com.xht.framework.core.blog.dto.BLogDTO;
import com.xht.framework.log.repository.BLogRepository;
import  com.xht.platform.audit.api.IBLogClient;
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
     * @param bLogDTO 日志DTO
     */
    @Override
    public void save(BLogDTO bLogDTO) {
        blogClient.saveLog(bLogDTO);
    }

}
