package com.xht.generate.cache;

import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import com.xht.generate.dao.GenTypeMappingDao;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 列类型管理器，负责缓存数据库类型与目标语言类型的映射关系
 * 缓存结构: dbTypeMap[数据库类型][目标语言类型][数据库字段类型] = 目标语言字段类型
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ColumnTypeMappingCache {

    private final GenTypeMappingDao genTypeMappingDao;

    /**
     * 类型映射缓存
     * 结构: [数据库类型][目标语言类型][数据库字段类型] = 目标语言字段类型
     */
    private static final Map<DataBaseTypeEnums, Map<LanguageTypeEnums, Map<String, GenTypeMappingEntity>>> DB_TYPE_MAPPING_CACHE = new HashMap<>();

    /**
     * 初始化缓存，项目启动时执行
     */
    @PostConstruct
    public void init() {
        long startTime = System.currentTimeMillis();
        try {
            refreshTypeMappingCache();
            log.info("类型映射缓存初始化完成，耗时: {}ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("类型映射缓存初始化失败", e);
        }
    }

    /**
     * 定时刷新缓存，每5分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void flushCache() {
        long startTime = System.currentTimeMillis();
        try {
            refreshTypeMappingCache();
            log.info("类型映射缓存刷新成功，耗时: {}ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("类型映射缓存刷新失败", e);
        }
    }

    /**
     * 获取数据库类型到目标语言类型的映射
     *
     * @param dbType       数据库类型
     * @param languageType 目标语言类型
     * @return 类型映射关系，如果不存在返回空Map
     */
    private Map<String, GenTypeMappingEntity> getTypeMapping(DataBaseTypeEnums dbType, LanguageTypeEnums languageType) {
        return DB_TYPE_MAPPING_CACHE.getOrDefault(dbType, new HashMap<>())
                .getOrDefault(languageType, new HashMap<>());
    }

    /**
     * 获取特定数据库类型和目标语言类型对应的目标字段类型
     *
     * @param dbType       数据库类型
     * @param languageType 目标语言类型
     * @param dbDataType   数据库字段类型
     * @return 目标语言字段类型，如果不存在返回null
     */
    public GenTypeMappingEntity getTargetType(DataBaseTypeEnums dbType, LanguageTypeEnums languageType, String dbDataType) {
        return getTypeMapping(dbType, languageType).get(dbDataType);
    }

    /**
     * 刷新类型映射缓存
     */
    private void refreshTypeMappingCache() {
        // 获取所有类型映射关系
        List<GenTypeMappingEntity> allMappings = genTypeMappingDao.findAll();

        // 按数据库类型分组
        Map<DataBaseTypeEnums, List<GenTypeMappingEntity>> dbTypeGroups =
                allMappings.stream()
                        .collect(Collectors.groupingBy(GenTypeMappingEntity::getDbType));

        // 构建三级缓存结构
        Map<DataBaseTypeEnums, Map<LanguageTypeEnums, Map<String, GenTypeMappingEntity>>> newCache = new HashMap<>(dbTypeGroups.size());

        dbTypeGroups.forEach((dbType, mappings) -> {
            // 按目标语言类型分组
            Map<LanguageTypeEnums, List<GenTypeMappingEntity>> languageGroups =
                    mappings.stream()
                            .collect(Collectors.groupingBy(GenTypeMappingEntity::getTargetLanguage));

            // 构建语言类型到字段类型的映射
            Map<LanguageTypeEnums, Map<String, GenTypeMappingEntity>> languageMappings = new HashMap<>(languageGroups.size());

            languageGroups.forEach((languageType, typeMappings) -> {
                Map<String, GenTypeMappingEntity> typeMap = typeMappings.stream()
                        .collect(Collectors.toMap(
                                GenTypeMappingEntity::getDbDataType,
                                (item) -> item,
                                (existing, replacement) -> {
                                    // 处理重复的数据库类型定义，保留第一个
                                    log.warn("数据库类型[{}]在语言[{}]中存在重复定义，将使用第一个值",
                                            existing, languageType.getValue());
                                    return existing;
                                }
                        ));
                languageMappings.put(languageType, typeMap);
            });
            newCache.put(dbType, languageMappings);
        });

        // 替换旧缓存
        DB_TYPE_MAPPING_CACHE.clear();
        DB_TYPE_MAPPING_CACHE.putAll(newCache);
    }
}
