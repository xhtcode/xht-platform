package com.xht.platform.client.blog;

import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.platform.audit.service.IBLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于本地数据库的操作日志仓库
 * <p>仅在 xht.blog.repository-type=jdbc 时装配，供 xht-admin 自身的 @BLog 落库；
 * 其它服务使用 jdbc 时不会注册该 Bean，因为落库能力依赖 admin 的 IBLogService。</p>
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class BLogJdbcRepository implements BLogRepository {

    private final IBLogService bLogService;

    /**
     * 保存日志
     *
     * @param bLog 日志DTO
     */
    @Override
    public void save(BLogDTO bLog) {
        // IBLogService#create 自带 @Async，不会阻塞请求线程
        bLogService.create(bLog);
    }
}
