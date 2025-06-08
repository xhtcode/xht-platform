package com.xht.framework.core.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 描述 ：资源解析工具类
 *
 * @author xht
 **/
public class ResourceUtils extends org.springframework.util.ResourceUtils {

    /**
     * 资源解析器.
     */
    private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    /**
     * 根据路径获取资源.
     *
     * @param location 路径
     * @return 资源
     */
    public static Resource getResource(String location) {
        return RESOLVER.getResource(location);
    }

}