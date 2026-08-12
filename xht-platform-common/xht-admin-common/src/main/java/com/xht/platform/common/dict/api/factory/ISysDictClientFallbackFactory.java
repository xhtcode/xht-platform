package com.xht.platform.common.dict.api.factory;

import com.xht.platform.common.dict.api.ISysDictClient;
import com.xht.platform.common.dict.api.fallback.ISysDictClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 描述：字典项查询服务熔断 fallback 工厂
 *
 * @author xht
 **/
@Slf4j
@Component
public class ISysDictClientFallbackFactory implements FallbackFactory<ISysDictClient> {

    /**
     * 创建 fallback 实例
     *
     * @param cause cause of an exception.
     * @return fallback 实例
     */
    @Override
    public ISysDictClient create(Throwable cause) {
        return new ISysDictClientFallback(cause);
    }

}
