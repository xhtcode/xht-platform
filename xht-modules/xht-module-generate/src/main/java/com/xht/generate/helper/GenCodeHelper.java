package com.xht.generate.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.generate.cache.ColumnTypeMappingCache;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.StringUtils;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 代码生成参数帮助类
 * 负责处理代码生成过程中的参数转换、Velocity上下文构建和模板解析
 *
 * @author xht
 **/
public final class GenCodeHelper {

    /**
     * 预生成的ID和UUID数量
     */
    private static final int ID_GENERATOR_COUNT = 10;

    /**
     * 私有构造器，禁止实例化
     */
    private GenCodeHelper() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 构建Velocity上下文对象，用于模板渲染
     *
     * @param genRequest     代码生成核心请求参数
     * @param tableInfo      表信息实体
     * @param columnInfoList 列信息实体列表
     * @return Velocity上下文对象，包含所有渲染所需数据
     */
    public static VelocityContext buildVelocityContext(GenCodeCoreRequest genRequest,
                                                       GenTableEntity tableInfo,
                                                       List<GenTableColumnEntity> columnInfoList) {
        // 获取类型映射缓存Bean，增加空判断
        ColumnTypeMappingCache typeMappingCache = SpringContextUtils.getBean(ColumnTypeMappingCache.class);
        // 用于收集需要导入的类
        Set<String> importClassNames = new HashSet<>();
        VelocityContext context = new VelocityContext();

        // 设置基础配置参数
        context.put("author", genRequest.getAuthor());
        context.put("packageName", genRequest.getPackageName());
        context.put("nowDate", DateUtil.now());

        // 添加表信息
        Map<String, Object> tableInfoMap = convertTableToMap(tableInfo);
        tableInfoMap.forEach(context::put);

        // 处理列信息
        List<Map<String, Object>> columnMaps = processColumnInfo(typeMappingCache, tableInfo, columnInfoList, importClassNames);
        context.put("columns", columnMaps);

        // 设置主键列信息
        GenTableColumnEntity pkColumn = columnInfoList.stream()
                .filter(col -> IdPrimaryKeyEnums.YES.equals(col.getDbPrimary()))
                .findFirst()
                .orElse(new GenTableColumnEntity());
        context.put("pkColumn", convertColumnToMap(typeMappingCache, tableInfo.getDataBaseType(), pkColumn, null));

        // 添加需要导入的类
        context.put("importClassNames", importClassNames);

        // 添加ID和UUID生成器
        addIdGenerators(context);

        return context;
    }

    /**
     * 处理列信息，转换为Map并收集导入类
     */
    private static List<Map<String, Object>> processColumnInfo(ColumnTypeMappingCache typeMappingCache,
                                                               GenTableEntity genTableEntity,
                                                               List<GenTableColumnEntity> columnInfoList,
                                                               Set<String> importClassNames) {
        if (Objects.isNull(columnInfoList) || columnInfoList.isEmpty()) {
            return Collections.emptyList();
        }

        DataBaseTypeEnums dbType = genTableEntity.getDataBaseType();
        List<Map<String, Object>> columnMaps = new ArrayList<>(columnInfoList.size());

        for (GenTableColumnEntity column : columnInfoList) {
            Map<String, Object> columnMap = convertColumnToMap(typeMappingCache, dbType, column, importClassNames);
            columnMaps.add(columnMap);
        }

        return columnMaps;
    }

    /**
     * 向上下文添加ID和UUID生成器
     */
    private static void addIdGenerators(VelocityContext context) {
        for (int i = 0; i < ID_GENERATOR_COUNT; i++) {
            int index = i + 1;
            context.put("id" + index, IdUtil.getSnowflakeNextId());
            context.put("uuid" + index, IdUtil.fastSimpleUUID());
        }
    }

    /**
     * 将表信息实体转换为Map，便于Velocity模板访问
     *
     * @param tableInfo 表信息实体
     * @return 转换后的Map集合
     */
    private static Map<String, Object> convertTableToMap(GenTableEntity tableInfo) {
        if (Objects.isNull(tableInfo)) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("engineName", tableInfo.getEngineName());
        map.put("tableName", tableInfo.getTableName());
        map.put("tableComment", tableInfo.getTableComment());
        map.put("codeName", tableInfo.getCodeName());
        map.put("codeComment", tableInfo.getCodeComment());
        map.put("tableCreateTime", tableInfo.getTableCreateTime());
        map.put("tableUpdateTime", tableInfo.getTableUpdateTime());

        // 处理表扩展配置
        map.put("moduleName", tableInfo.getModuleName());
        map.put("serviceName", tableInfo.getServiceName());
        map.put("url", "");
        map.put("authorizationPrefix", tableInfo.getPermissionPrefix());

        return map;
    }

    /**
     * 将列信息实体转换为Map，便于Velocity模板访问
     *
     * @param typeMappingCache  数据库类型字段映射缓存
     * @param dbType            数据库类型
     * @param columnInfo        列信息实体
     * @param importClassNames  需导入的类名集合
     * @return 转换后的Map集合
     */
    private static Map<String, Object> convertColumnToMap(ColumnTypeMappingCache typeMappingCache,
                                                          DataBaseTypeEnums dbType,
                                                          GenTableColumnEntity columnInfo,
                                                          Set<String> importClassNames) {
        if (Objects.isNull(columnInfo)) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = new HashMap<>(32);

        // 基础列信息
        map.put("id", columnInfo.getId());
        map.put("tableId", columnInfo.getTableId());
        map.put("codeName", columnInfo.getCodeName());
        map.put("codeComment", columnInfo.getCodeComment());
        map.put("sortOrder", columnInfo.getSortOrder());

        // 状态枚举值转换（存储实际值而非枚举对象）


        // 扩展配置信息


        // 处理各语言类型的字段映射
        processLanguageTypeMappings(typeMappingCache, dbType, columnInfo, map, importClassNames);

        return map;
    }

    /**
     * 处理各语言类型的字段映射关系
     */
    private static void processLanguageTypeMappings(ColumnTypeMappingCache typeMappingCache,
                                                    DataBaseTypeEnums dbType,
                                                    GenTableColumnEntity columnInfo,
                                                    Map<String, Object> columnMap,
                                                    Set<String> importClassNames) {
        for (LanguageTypeEnums languageType : LanguageTypeEnums.values()) {
            GenTypeMappingEntity typeMapping = typeMappingCache.getTargetType(dbType, languageType, columnInfo.getDbType());
            if (Objects.nonNull(typeMapping)) {
                columnMap.put(languageType.getShortName(), typeMapping.getTargetDataType());
                // 收集需要导入的类
                String importPackage = typeMapping.getImportPackage();
                if (StringUtils.hasText(importPackage) && Objects.nonNull(importClassNames)) {
                    importClassNames.add(importPackage);
                }
            } else {
                // 使用默认类型
                columnMap.put(languageType.getShortName(), languageType.getDefaultType());
            }
        }
    }

    /**
     * 获取状态枚举值，默认返回NO的value
     */
    private static Integer getStatusValue(GenStatusEnums status) {
        return Objects.requireNonNullElse(status, GenStatusEnums.NO).getValue();
    }

    /**
     * 解析模板列表为代码核心业务对象列表
     *
     * @param templateList 模板实体列表
     * @return 代码核心业务对象列表，包含文件路径和模板内容
     */
    public static List<GenCodeCoreBo> parseTemplates(List<GenTemplateEntity> templateList) {
        if (Objects.isNull(templateList) || templateList.isEmpty()) {
            return Collections.emptyList();
        }

        return templateList.stream()
                .map(GenCodeHelper::convertToGenCodeCoreBo)
                .collect(Collectors.toList());
    }

    /**
     * 将模板实体转换为代码核心业务对象
     */
    private static GenCodeCoreBo convertToGenCodeCoreBo(GenTemplateEntity template) {
        // 使用Path处理路径，避免手动拼接斜杠的问题
        Path filePath = Paths.get(template.getFilePathTemplate())
                .resolve(template.getFileNameTemplate())
                .normalize();

        return new GenCodeCoreBo(filePath.toString(), template.getContent());
    }

    /**
     * 根据Velocity上下文和代码核心业务对象列表生成代码
     *
     * @param velocityContext Velocity上下文
     * @param codeCoreList    代码核心业务对象列表
     */
    public static void generateCode(VelocityContext velocityContext, List<GenCodeCoreBo> codeCoreList) {
        if (Objects.isNull(velocityContext) || Objects.isNull(codeCoreList) || codeCoreList.isEmpty()) {
            return;
        }

        codeCoreList.forEach(codeCore -> {
            // 渲染文件路径
            String resolvedPath = renderTemplate(velocityContext, "filePathTemplate", codeCore.getFilePath());
            // 渲染文件内容
            String resolvedCode = renderTemplate(velocityContext, "codeTemplate", codeCore.getCode());

            codeCore.setFilePath(resolvedPath);
            codeCore.setCode(resolvedCode);
        });
    }

    /**
     * 使用Velocity模板引擎渲染模板内容
     *
     * @param context         Velocity上下文
     * @param templateName    模板名称（用于日志和错误提示）
     * @param templateContent 模板内容
     * @return 渲染后的字符串
     */
    private static String renderTemplate(VelocityContext context, String templateName, String templateContent) {
        if (!StringUtils.hasText(templateName) || !StringUtils.hasText(templateContent)) {
            return StrUtil.EMPTY;
        }

        try (StringWriter writer = new StringWriter()) {
            Velocity.evaluate(context, writer, templateName, templateContent);
            return writer.toString().trim(); // 去除首尾空白
        } catch (Exception e) {
            throw new UtilException("模板渲染失败，模板名称：" + templateName, e);
        }
    }
}
