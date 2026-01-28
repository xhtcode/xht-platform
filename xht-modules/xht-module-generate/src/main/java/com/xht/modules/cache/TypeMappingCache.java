package com.xht.modules.cache;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.modules.common.enums.DataBaseTypeEnums;
import com.xht.modules.generate.dao.GenTypeMappingDao;
import com.xht.modules.generate.entity.GenTypeMappingEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
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
public class TypeMappingCache {

    /**
     * 类型映射缓存
     * 结构: [数据库类型][数据库字段类型] = 目标语言字段类型
     */
    private static final Map<DataBaseTypeEnums, Map<String, GenTypeMappingEntity>> DB_TYPE_MAPPING_CACHE_TEMP = new HashMap<>();
    private final GenTypeMappingDao genTypeMappingDao;

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
     * 获取指定数据库类型和目标语言类型的所有类型映射实体集合
     *
     * @param dbType 数据库类型
     * @return 类型映射实体集合
     */
    public List<GenTypeMappingEntity> getTypeMappingList(DataBaseTypeEnums dbType) {
        Map<String, GenTypeMappingEntity> cacheTempOrDefault = DB_TYPE_MAPPING_CACHE_TEMP.getOrDefault(dbType, Collections.emptyMap());
        //@formatter:off
        return cacheTempOrDefault
                .values()
                .stream()
                .sorted(Comparator.comparing(GenTypeMappingEntity::getDbDataType))
                .collect(Collectors.toList());
        //@formatter:on
    }


    public GenTypeMappingEntity getTargetType(DataBaseTypeEnums dbType, String dbDataType) {
        Map<String, GenTypeMappingEntity> cacheTempOrDefault = DB_TYPE_MAPPING_CACHE_TEMP.getOrDefault(dbType, Collections.emptyMap());
        GenTypeMappingEntity mappingEntity = cacheTempOrDefault.get(dbDataType);
        ThrowUtils.notNull(mappingEntity, "未找到类型映射关系");
        return mappingEntity;
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
        // 替换旧缓存
        DB_TYPE_MAPPING_CACHE_TEMP.clear();
        dbTypeGroups.forEach((dbType, mappings) -> {
            Map<String, GenTypeMappingEntity> newCache = new HashMap<>(dbTypeGroups.size());
            for (GenTypeMappingEntity mapping : mappings) {
                newCache.put(mapping.getDbDataType(), mapping);
            }
            DB_TYPE_MAPPING_CACHE_TEMP.put(dbType, newCache);
        });


    }
}
