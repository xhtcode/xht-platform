package com.xht.modules.admin.dict;

import com.xht.framework.cache.service.RedisService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ROptional;
import com.xht.modules.admin.dict.api.ISysDictClient;
import com.xht.platform.common.PlatFormProperties;
import com.xht.platform.common.dict.ISysDictFactory;
import com.xht.platform.common.dict.domain.vo.DictVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 字典项查询服务工厂
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysDictApiFactory implements ISysDictFactory {

    @Resource
    private ISysDictClient sysDictClient;

    @Resource
    private RedisService redisService;

    @Resource
    private PlatFormProperties platFormProperties;

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Override
    public List<DictVo> getDictList(String dictCode) {
        return redisService.getSet(platFormProperties.getDictCacheKey(dictCode), platFormProperties.getDictTimeOut(), () -> {
            R<List<DictVo>> byDictCode = sysDictClient.getByDictCode(dictCode);
            return ROptional.of(byDictCode).get().orElse(Collections.emptyList());
        });
    }
}
