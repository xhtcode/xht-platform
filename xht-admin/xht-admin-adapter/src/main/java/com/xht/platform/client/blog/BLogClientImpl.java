package com.xht.platform.client.blog;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.audit.service.IBLogService;
import com.xht.platform.common.audit.api.IBLogClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：日志存储模块
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequestMapping("/api/sys/log")
@RequiredArgsConstructor
public class BLogClientImpl implements IBLogClient {

    private final IBLogService ibLogService;

    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/save")
    public R<Void> saveLog(@RequestBody BLogDTO bLogDTO) {
        ibLogService.create(bLogDTO);
        return R.ok().build();
    }

}
