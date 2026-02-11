package com.xht.modules.admin.dict;

import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.properties.XhtConfigProperties;
import com.xht.framework.core.properties.cache.CacheProperties;
import com.xht.framework.core.support.dict.ISysDictFactory;
import com.xht.framework.core.support.dict.domain.DictVo;
import com.xht.framework.core.utils.ROptional;
import com.xht.modules.admin.dict.api.ISysDictClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 字典项查询服务工厂
 *
 * @author xht
 **/
@Slf4j
public class SysDictApiFactory implements ISysDictFactory {

    @Resource
    private ISysDictClient sysDictClient;

    @Resource
    private RedisRepository redisRepository;

    @Resource
    private XhtConfigProperties xhtConfigProperties;

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Override
    public List<DictVo> getDictList(String dictCode) {
        CacheProperties dictCache = Optional
                .ofNullable(xhtConfigProperties)
                .map(XhtConfigProperties::getGlobal)
                .map(XhtConfigProperties.GlobalConfigProperties::getDict)
                .orElseThrow(() -> new BusinessException("字典配置查询不到"));
        return redisRepository.getSet(dictCache.getDictCacheKey(dictCode), dictCache.timeOut(), dictCache.unit(), () -> {
            R<List<DictVo>> byDictCode = sysDictClient.getByDictCode(dictCode);
            return ROptional.of(byDictCode).get().orElse(Collections.emptyList());
        });
    }
}
