package com.xht.framework.file.configurers;

import com.xht.framework.file.annotations.ConditionalOnFile;
import com.xht.framework.file.constant.FileConstant;
import com.xht.framework.file.enums.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * 描述： 文件存储条件判断
 *
 * @author xht
 **/
@Slf4j
public class FileStorageCondition implements Condition {

    /**
     * 判断是否匹配
     *
     * @param context  条件判断上下文
     * @param metadata 注解元数据
     * @return 是否匹配
     */
    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        // 获取注解 @ConditionalOnFile
        MergedAnnotation<ConditionalOnFile> annotation = metadata.getAnnotations().get(ConditionalOnFile.class);
        if (!annotation.isPresent()) {
            return false;
        }
        // 注解上声明要匹配的类型
        StorageType expectType = annotation.getValue("value", StorageType.class).orElse(StorageType.LOCAL);
        // 读取yml/properties配置
        StorageType configType = StorageType.getByValueIgnoreCase(context.getEnvironment().getProperty(FileConstant.CONFIG_STORAGE_TYPE_KEY));
        if (Objects.isNull(configType)) {
            configType = StorageType.LOCAL;
        }
        boolean match = Objects.equals(expectType, configType);
        log.debug("文件存储条件判断：注解期望={}，配置值={}，匹配结果={}", expectType, configType, match);
        return match;
    }
}
