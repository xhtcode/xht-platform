package com.xht.platform.dict.client;

import com.xht.framework.common.domain.R;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import com.xht.platform.common.AdminConstants;
import com.xht.platform.common.dict.api.ISysDictClient;
import com.xht.platform.common.dict.domain.DictVO;
import com.xht.platform.dict.client.factory.SysDictClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 描述：字典项查询服务的 Feign 客户端
 * <p>
 * 契约（方法签名与出入参）来自 {@link ISysDictClient}，本接口只承载 Feign 与 Web 注解，
 * 因此 common 不需要依赖 openfeign。方法注解必须在子接口重声明，不依赖父接口注解继承。
 *
 * @author xht
 **/
@FeignClient(
        name = AdminConstants.APPLICATION_NAME,
        contextId = "iSysDictClient",
        path = "/api/sys/dict",
        fallbackFactory = SysDictClientFallbackFactory.class
)
public interface SysDictFeignClient extends ISysDictClient {

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Override
    @FeignIgnoreAuth
    @GetMapping("/code/{dictCode}")
    R<List<DictVO>> getByDictCode(@PathVariable("dictCode") String dictCode);

}
