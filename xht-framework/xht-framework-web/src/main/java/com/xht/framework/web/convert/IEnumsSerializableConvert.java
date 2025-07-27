package com.xht.framework.web.convert;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * String 转 IEnum 枚举的转换器
 * 支持通过枚举值或枚举名称进行转换
 *
 * @author xht
 */
@Slf4j
public class IEnumsSerializableConvert implements Converter<String, IEnum<Serializable>> {

    /**
     * 存储枚举值与枚举实例的映射关系
     */
    private final Map<String, IEnum<Serializable>> enumValueMap = new ConcurrentHashMap<>();

    /**
     * 存储枚举名称与枚举实例的映射关系
     */
    private final Map<String, IEnum<Serializable>> enumNameMap = new ConcurrentHashMap<>();

    /**
     * 构造函数，初始化枚举映射关系
     *
     * @param enumType 枚举类型
     */
    public IEnumsSerializableConvert(Class<? extends IEnum<Serializable>> enumType) {
        // 验证枚举类型
        if (!enumType.isEnum()) {
            throw new BusinessException("无效的枚举类型: " + enumType.getName());
        }
        initializeEnumMaps(enumType);
    }

    /**
     * 初始化枚举映射关系
     *
     * @param enumType 枚举类型
     */
    private void initializeEnumMaps(Class<? extends IEnum<Serializable>> enumType) {
        IEnum<Serializable>[] enumConstants = enumType.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            log.warn("枚举类型 {} 没有定义任何枚举常量", enumType.getName());
            return;
        }
        for (IEnum<Serializable> enumInstance : enumConstants) {
            try {
                // 处理枚举值映射
                Serializable enumValue = enumInstance.getValue();
                String valueStr = StringUtils.str(enumValue);
                if (StringUtils.isEmpty(valueStr)) {
                    throw new BusinessException(
                            String.format("枚举 %s 的值不能为空，请检查枚举实现", enumInstance.getClass().getName()));
                }
                enumValueMap.put(valueStr, enumInstance);

                // 处理枚举名称映射
                if (enumInstance instanceof Enum<?>) {
                    String enumName = ((Enum<?>) enumInstance).name();
                    enumNameMap.put(enumName, enumInstance);
                }
            } catch (Exception e) {
                log.error("初始化枚举映射关系失败，枚举实例: {}", enumInstance, e);
                throw new BusinessException("初始化枚举转换器失败: " + e.getMessage());
            }
        }
    }

    /**
     * 将字符串转换为对应的枚举实例
     * 优先通过枚举值匹配，其次通过枚举名称匹配
     *
     * @param source 源字符串
     * @return 对应的枚举实例，若未找到则返回null
     */
    @Override
    public IEnum<Serializable> convert(String source) {
        return enumValueMap.getOrDefault(source, enumNameMap.get(source));
    }
}
