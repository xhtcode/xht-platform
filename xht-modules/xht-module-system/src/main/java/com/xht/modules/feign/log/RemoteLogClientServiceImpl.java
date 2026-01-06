package com.xht.modules.feign.log;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.system.service.ISysLogService;
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
public class RemoteLogClientServiceImpl {

    private final ISysLogService sysLogService;

    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    @IgnoreAuth
    @PostMapping("/api/sys/log/save")
    public R<Void> saveLog(@RequestBody BLogDTO bLogDTO) {
        sysLogService.create(bLogDTO);
        return R.ok();
    }
}