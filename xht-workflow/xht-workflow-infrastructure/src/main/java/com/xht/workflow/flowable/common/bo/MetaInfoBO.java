package com.xht.workflow.flowable.common.bo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 模型元信息建造器
 * 说明： 非单例，每次builder()创建独立实例；实例不可多线程共享，用完即弃
 *
 * @author xht
 **/
public final class MetaInfoBO {

    private final Map<String, Object> metaInfo;

    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<>() {
    };


    private MetaInfoBO() {
        this.metaInfo = new HashMap<>();
    }

    /**
     * 创建建造者实例，每次返回全新对象，线程隔离
     */
    public static MetaInfoBO builder() {
        return new MetaInfoBO();
    }

    /**
     * 填充元数据键值对
     *
     * @param key   元信息key
     * @param value 元信息值
     * @return 当前建造者（同一实例，不可跨线程复用）
     */
    public MetaInfoBO put(String key, Object value) {
        metaInfo.put(key, value);
        return this;
    }

    public MetaInfoBO putAll(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return this;
        }
        metaInfo.putAll(map);
        return this;
    }

    public MetaInfoBO of(String metaInfoStr) {
        if (StringUtils.hasText(metaInfoStr)) {
            Map<String, Object> object = JsonUtils.toObject(metaInfoStr, MAP_TYPE_REFERENCE);
            if (CollectionUtils.isEmpty(object)) {
                return this;
            }
            metaInfo.putAll(object);
        }
        return this;
    }

    /**
     * 获取元数据字符串
     */
    public String getMetaInfoStr() {
        return JsonUtils.toJsonString(metaInfo);
    }

    public Map<String, Object> getMetaInfo() {
        return metaInfo;
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(metaInfo.get(key));
    }

}