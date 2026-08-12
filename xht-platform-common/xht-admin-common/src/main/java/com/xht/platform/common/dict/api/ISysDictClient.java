package com.xht.platform.common.dict.api;

import com.xht.framework.common.domain.R;
import com.xht.platform.common.AdminConstants;
import com.xht.platform.common.dict.domain.DictVO;
import com.xht.platform.common.dict.api.factory.ISysDictClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 描述：字典项查询服务
 *
 * @author xht
 **/
@FeignClient(
        name = AdminConstants.APPLICATION_NAME,
        contextId = "iSysDictClient",
        path = "/api/sys/dict",
        fallbackFactory = ISysDictClientFallbackFactory.class
)
public interface ISysDictClient {

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @GetMapping("/code/{dictCode}")
    R<List<DictVO>> getByDictCode(@PathVariable String dictCode);

}