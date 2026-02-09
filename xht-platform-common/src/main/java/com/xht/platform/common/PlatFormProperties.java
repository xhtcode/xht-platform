package com.xht.platform.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 字典项属性
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = "xht.system")
public class PlatFormProperties {

    /**
     * 字典项属性
     */
    private Dict dict = new Dict();

    @Data
    public static class Dict {

        /**
         * 字典项缓存Key
         */
        private String cacheKey = "xht:dict:%s";

        /**
         * 字典项缓存时间
         */
        private Long timeOut = 5 * 60 * 1000L;

    }

    /**
     * 获取字典项缓存时间
     *
     * @return 字典项缓存时间
     */
    public Long getDictTimeOut() {
        return Optional.ofNullable(dict).map(Dict::getTimeOut).orElse(5 * 60 * 1000L);
    }

    public String getDictCacheKey(String dictCode) {
        return String.format(Optional.ofNullable(dict).map(Dict::getCacheKey).orElse("xht:dict:%s"), dictCode);
    }

}
