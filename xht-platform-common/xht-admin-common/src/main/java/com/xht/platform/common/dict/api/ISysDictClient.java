package com.xht.platform.common.dict.api;

import com.xht.framework.common.domain.R;
import com.xht.platform.common.dict.domain.DictVO;

import java.util.List;

/**
 * 描述：字典项查询服务契约
 * <p>
 * 只声明方法签名与出入参，不携带 Feign / Web 注解；Feign 客户端为
 * {@code com.xht.platform.dict.client.SysDictFeignClient}，服务端实现为
 * {@code com.xht.platform.client.dict.SysDictClientImpl}。
 *
 * @author xht
 **/
public interface ISysDictClient {

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    R<List<DictVO>> getByDictCode(String dictCode);

}
