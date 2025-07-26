package com.xht.system.feign.log;

import com.xht.cloud.log.feign.RemoteLogClientService;
import com.xht.framework.core.domain.R;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import com.xht.system.modules.log.service.ISysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志存储
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class RemoteLogClientServiceImpl implements RemoteLogClientService {

    private final ISysLogService sysLogService;

    /**
     * 存储日志
     *
     * @param logDTO 日志保存信息
     * @return 保存结果
     */
    @Override
    @PostMapping("/api/sys/log/save")
    @NoAuthentication
    public R<Boolean> saveLog(@RequestBody LogDTO logDTO) {
        return R.ok(sysLogService.create(logDTO));
    }
}