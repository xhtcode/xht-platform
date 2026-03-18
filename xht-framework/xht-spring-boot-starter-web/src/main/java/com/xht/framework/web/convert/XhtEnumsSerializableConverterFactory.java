package com.xht.framework.web.convert;

import com.xht.framework.core.enums.XhtEnum;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IEnum枚举类型转换器工厂
 * 负责为不同的IEnum实现类创建对应的转换器实例
 *
 * @author xht
 */
@Slf4j
public class XhtEnumsSerializableConverterFactory implements ConverterFactory<String, XhtEnum<Serializable>> {

    /**
     * 缓存转换器实例，key为枚举类型，value为对应的转换器
     */
    private static final Map<Class<? extends XhtEnum<Serializable>>, Converter<String, ? extends XhtEnum<Serializable>>> CONVERTER_CACHE =
            new ConcurrentHashMap<>();

    /**
     * 获取指定枚举类型的转换器
     * 采用懒加载模式，首次使用时创建转换器并缓存
     *
     * @param targetType 目标枚举类型
     * @param <T>        枚举类型泛型，必须实现IEnum<Serializable>
     * @return 对应类型的转换器
     * @throws IllegalArgumentException 如果目标类型不是有效的枚举类型
     */
    @Override
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T extends XhtEnum<Serializable>> Converter<String, T> getConverter(@Nonnull Class<T> targetType) {
        // 验证目标类型有效性
        validateTargetType(targetType);
        // 从缓存获取或创建转换器
        Converter<String, ? extends XhtEnum<Serializable>> converter = CONVERTER_CACHE.get(targetType);
        if (converter == null) {
            log.debug("为枚举类型 [{}] 创建新的转换器实例", targetType.getName());
            converter = new XhtEnumsSerializableConvert(targetType);
            CONVERTER_CACHE.put(targetType, converter);
        }
        return (Converter<String, T>) converter;
    }

    /**
     * 验证目标类型是否为有效的枚举类型
     *
     * @param targetType 目标类型
     * @throws IllegalArgumentException 如果类型无效则抛出异常
     */
    private <T extends XhtEnum<Serializable>> void validateTargetType(Class<T> targetType) {
        if (!targetType.isEnum()) {
            throw new IllegalArgumentException("目标类型 [" + targetType.getName() + "] 不是枚举类型");
        }
        if (!XhtEnum.class.isAssignableFrom(targetType)) {
            throw new IllegalArgumentException("目标类型 [" + targetType.getName() + "] 未实现XhtEnum接口");
        }
    }
}
