package com.xht.platform.blog.client;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import com.xht.platform.blog.client.factory.BLogClientFallbackFactory;
import com.xht.platform.common.AdminConstants;
import com.xht.platform.common.audit.api.IBLogClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述：日志存储的 Feign 客户端
 * <p>
 * 契约（方法签名与出入参）来自 {@link IBLogClient}，本接口只承载 Feign 与 Web 注解，
 * 因此 common 不需要依赖 openfeign。方法注解必须在子接口重声明，不依赖父接口注解继承。
 *
 * @author xht
 **/
@FeignClient(
        name = AdminConstants.APPLICATION_NAME,
        contextId = "bLogClient",
        path = "/api/sys/log",
        fallbackFactory = BLogClientFallbackFactory.class
)
public interface BLogFeignClient extends IBLogClient {

    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    @Override
    @FeignIgnoreAuth
    @PostMapping("/save")
    R<Void> saveLog(@RequestBody BLogDTO bLogDTO);

}
