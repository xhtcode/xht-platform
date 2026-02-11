package com.xht.modules.admin.dict.api;

import com.xht.framework.core.domain.R;
import com.xht.modules.admin.dict.api.factory.ISysDictClientFallbackFactory;
import com.xht.framework.core.support.dict.domain.DictVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 字典项查询服务
 * @author xht
 **/
@FeignClient(contextId = "iSysDictClient", value = "xht-module-admin", fallbackFactory = ISysDictClientFallbackFactory.class)
public interface ISysDictClient {

    @GetMapping("/api/sys/dict/code/{dictCode}")
    R<List<DictVo>> getByDictCode(@PathVariable String dictCode);

}
