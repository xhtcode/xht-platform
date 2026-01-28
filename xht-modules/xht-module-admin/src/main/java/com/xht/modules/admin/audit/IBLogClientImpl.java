package com.xht.modules.admin.audit;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.admin.audit.api.IBLogClient;
import com.xht.modules.admin.audit.service.IBLogService;
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
public class IBLogClientImpl implements IBLogClient {

    private final IBLogService ibLogService;

    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    @IgnoreAuth
    @PostMapping("/api/sys/log/save")
    public R<Void> saveLog(@RequestBody BLogDTO bLogDTO) {
        ibLogService.create(bLogDTO);
        return R.ok();
    }

}