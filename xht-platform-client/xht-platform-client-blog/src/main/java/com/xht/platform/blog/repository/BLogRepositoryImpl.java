package com.xht.platform.blog.repository;

import com.xht.framework.common.constant.RConstants;
import com.xht.framework.common.domain.R;
import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.platform.common.audit.api.IBLogClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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
     * <p>通过 Feign 上报到 xht-admin；上报失败只记录 error，不影响业务（调用方切面亦已兜底）。</p>
     *
     * @param bLog 日志DTO
     */
    @Override
    public void save(BLogDTO bLog) {
        try {
            R<Void> result = blogClient.saveLog(bLog);
            if (result == null || !Objects.equals(result.getCode(), RConstants.SUCCESS)) {
                log.error("操作日志上报失败，title={}，description={}，result={}",
                        bLog.getTitle(), bLog.getLogDesc(), result);
            }
        } catch (Exception e) {
            log.error("操作日志上报异常，title={}，description={}", bLog.getTitle(), bLog.getLogDesc(), e);
        }
    }
}
