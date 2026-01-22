package com.xht.modules.admin.notice;

import com.xht.framework.core.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.admin.notice.service.ISysMessageService;
import com.xht.platform.common.notice.core.MessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 ： 系统管理-站内信收件人明细表
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class SysMessageClientImpl {

    private final ISysMessageService sysMessageService;

    /**
     * 发送 站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @IgnoreAuth
    @PostMapping("/api/sys/message/send")
    public R<Void> sendMessage(@RequestBody MessagePayload payload) {
        sysMessageService.sendMessage(payload);
        return R.ok();
    }

}
