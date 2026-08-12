package com.xht.platform.client.message;

import com.xht.framework.common.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.common.message.api.ISysMessageClient;
import com.xht.platform.common.message.core.MessagePayload;
import com.xht.platform.notice.service.ISysMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：系统管理-站内信
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequestMapping("/api/sys/message")
@RequiredArgsConstructor
public class SysMessageClientImpl implements ISysMessageClient {

    private final ISysMessageService sysMessageService;

    /**
     * 发送 站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @IgnoreAuth
    @PostMapping("/send")
    public R<Void> sendMessage(@RequestBody MessagePayload payload) {
        sysMessageService.sendMessage(payload);
        return R.ok().build();
    }

}
