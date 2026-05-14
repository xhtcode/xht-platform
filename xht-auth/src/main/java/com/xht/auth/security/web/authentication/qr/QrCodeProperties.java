package com.xht.auth.security.web.authentication.qr;

import cn.hutool.core.util.StrUtil;
import com.xht.framework.cache.constant.CacheConstant;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.security.constant.SecurityConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 二维码属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "xht.security.qr.code")
public class QrCodeProperties {

    /**
     * 二维码过期时间
     */
    private Long timeout = SecurityConstant.QR_CODE_INFO_TIMEOUT;

    /**
     * 二维码redis缓存信息前缀
     */
    private String keyPrefix = SecurityConstant.QR_CODE_KEY_PREFIX;


    /**
     * 获取二维码缓存信息key
     * @param key 二维码信息
     * @return 二维码缓存信息key
     */
    public String formatterKey(String... key) {
        String keyPrefix = StrUtil.removeSuffix(this.keyPrefix, CacheConstant.REDIS_KEY_DELIMITED);
        return Keys.createKey(keyPrefix, key);
    }

}