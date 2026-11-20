package com.xht.platform.dict.factory;

import com.xht.framework.common.domain.R;
import com.xht.framework.utils.ROptional;
import com.xht.platform.common.dict.api.ISysDictClient;
import com.xht.platform.common.dict.domain.DictVO;
import com.xht.platform.common.dict.factory.ISysDictFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * 字典项查询服务工厂
 *
 * @author xht
 **/
@Slf4j
public class SysDictApiFactory implements ISysDictFactory {

    @Resource
    private ISysDictClient sysDictClient;

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Override
    public List<DictVO> getDictList(String dictCode) {
        R<List<DictVO>> byDictCode = sysDictClient.getByDictCode(dictCode);
        return ROptional.of(byDictCode).get().orElse(Collections.emptyList());
    }
}
