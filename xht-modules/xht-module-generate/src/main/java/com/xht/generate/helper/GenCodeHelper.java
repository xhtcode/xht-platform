package com.xht.generate.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.generate.cache.ColumnTypeMappingCache;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import com.xht.generate.domain.ColumnExtConfig;
import com.xht.generate.domain.TableExtConfig;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.StringUtils;

import java.io.StringWriter;
import java.util.*;

/**
 * 代码生成参数帮助类
 * 负责处理代码生成过程中的参数转换、上下文构建和模板解析
 *
 * @author xht
 **/
public final class GenCodeHelper {

    /**
     * 私有构造器，禁止实例化
     */
    private GenCodeHelper() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 生成Velocity上下文对象，用于模板渲染
     *
     * @param genRequest      代码生成核心请求参数
     * @param tableInfo       表信息实体
     * @param columnInfoList 列信息实体列表
     * @return Velocity上下文对象，包含所有渲染所需数据
     */
    public static VelocityContext buildVelocityContext(GenCodeCoreRequest genRequest, GenTableInfoEntity tableInfo, List<GenColumnInfoEntity> columnInfoList) {
        ColumnTypeMappingCache columnTypeMappingCache = SpringContextUtils.getBean(ColumnTypeMappingCache.class);
        Set<String> importColumnNames = new HashSet<>();
        VelocityContext context = new VelocityContext();
        // 基础配置参数
        context.put("author", genRequest.getAuthor());
        context.put("packageName", genRequest.getPackageName());
        context.put("nowDate", DateUtil.now());
        Map<String, Object> tableInfoMap = convertToMap(tableInfo);
        tableInfoMap.forEach(context::put);
        // 列信息列表
        List<Map<String, Object>> columns = new ArrayList<>(columnInfoList.size());
        for (GenColumnInfoEntity column : columnInfoList) {
            columns.add(convertToMap(columnTypeMappingCache, tableInfo.getDataBaseType(), column, importColumnNames));
        }
        context.put("pkColumn", columnInfoList.stream().filter(item -> Objects.equals(item.getIsPrimary(), GenStatusEnums.YES)).findFirst().orElse(new GenColumnInfoEntity()));
        context.put("columns", columns);
        context.put("importColumnNames", importColumnNames);
        // 添加ID和UUID生成器（预生成10组）
        addIdGenerators(context);
        return context;
    }

    /**
     * 向上下文添加ID和UUID生成器
     */
    private static void addIdGenerators(VelocityContext context) {
        for (int i = 0; i < 10; i++) {
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
    private static Map<String, Object> convertToMap(GenTableInfoEntity tableInfo) {
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
        TableExtConfig tableExtConfig = Objects.requireNonNullElse(tableInfo.getExtConfig(), new TableExtConfig());
        map.put("moduleName", tableExtConfig.getModuleName());
        map.put("serviceName", tableExtConfig.getServiceName());
        map.put("url", tableExtConfig.getUrl());
        map.put("authorizationPrefix", tableExtConfig.getAuthorizationPrefix());
        return map;
    }

    /**
     * 将列信息实体转换为Map，便于Velocity模板访问
     *
     * @param columnTypeMappingCache 数据库类型字段映射
     * @param dataBaseTypeEnums      数据库类型
     * @param columnInfo             列信息实体
     * @param importColumnNames      导入字段的包名
     * @return 转换后的Map集合
     */
    private static Map<String, Object> convertToMap(ColumnTypeMappingCache columnTypeMappingCache, DataBaseTypeEnums dataBaseTypeEnums, GenColumnInfoEntity columnInfo, Set<String> importColumnNames) {
        if (Objects.isNull(columnInfo)) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = new HashMap<>(32);
        ColumnExtConfig extConfig = Objects.requireNonNullElse(columnInfo.getExtConfig(), new ColumnExtConfig());
        // 基础列信息
        map.put("id", columnInfo.getId());
        map.put("tableId", columnInfo.getTableId());
        map.put("columnName", columnInfo.getColumnName());
        map.put("dbDataType", columnInfo.getDbDataType());
        map.put("columnComment", columnInfo.getColumnComment());
        map.put("columnLength", columnInfo.getColumnLength());
        map.put("codeName", columnInfo.getCodeName());
        map.put("codeComment", columnInfo.getCodeComment());
        map.put("sortOrder", columnInfo.getSortOrder());

        // 状态枚举值转换（存储实际值而非枚举对象）
        map.put("isRequired", Objects.requireNonNullElse(columnInfo.getIsRequired(), GenStatusEnums.NO).getValue());
        map.put("isPrimary", Objects.requireNonNullElse(columnInfo.getIsPrimary(), GenStatusEnums.NO).getValue());

        // 扩展配置信息
        map.put("entity", Objects.requireNonNullElse(extConfig.getEntity(), GenStatusEnums.NO).getValue());
        map.put("formItem", Objects.requireNonNullElse(extConfig.getFormItem(), GenStatusEnums.NO).getValue());
        map.put("formRequired", Objects.requireNonNullElse(extConfig.getFormRequired(), GenStatusEnums.NO).getValue());
        map.put("formType", extConfig.getFormType());
        map.put("formValidator", Objects.requireNonNullElse(extConfig.getFormValidator(), GenStatusEnums.NO).getValue());
        map.put("listItem", Objects.requireNonNullElse(extConfig.getList(), GenStatusEnums.NO).getValue());
        map.put("listSort", Objects.requireNonNullElse(extConfig.getListSort(), GenStatusEnums.NO).getValue());
        map.put("queryItem", Objects.requireNonNullElse(extConfig.getQueryItem(), GenStatusEnums.NO).getValue());
        map.put("queryType", extConfig.getQueryType());
        map.put("queryFormType", extConfig.getQueryFormType());
        map.put("extConfig", extConfig);
        for (LanguageTypeEnums item : LanguageTypeEnums.values()) {
            GenTypeMappingEntity targetType = columnTypeMappingCache.getTargetType(dataBaseTypeEnums, item, columnInfo.getDbDataType());
            if (Objects.nonNull(targetType)) {
                map.put(item.getShortName(), targetType.getTargetDataType());
                if (StringUtils.hasLength(targetType.getImportPackage())) {
                    importColumnNames.add(targetType.getImportPackage());
                }
            } else {
                map.put(item.getShortName(), item.getDefaultType());
            }
        }
        return map;
    }

    /**
     * 解析模板列表为代码核心业务对象列表
     *
     * @param templateList 模板实体列表
     * @return 代码核心业务对象列表，包含文件路径和模板内容
     */
    public static List<GenCodeCoreBo> parseTemplates(List<GenTemplateEntity> templateList) {
        if (Objects.isNull(templateList)) {
            return Collections.emptyList();
        }

        List<GenCodeCoreBo> result = new ArrayList<>(templateList.size());
        for (GenTemplateEntity template : templateList) {
            // 处理文件路径，移除多余的斜杠
            String filePath = StrUtil.removeSuffix(template.getFilePathTemplate(), StringConstant.SEPARATOR_SLASH);
            String fileName = StrUtil.removePrefix(template.getFileNameTemplate(), StringConstant.SEPARATOR_SLASH);
            result.add(new GenCodeCoreBo(filePath + StringConstant.SEPARATOR_SLASH + fileName, template.getContent()));
        }
        return result;
    }

    /**
     * 根据Velocity上下文和代码核心业务对象列表生成代码
     *
     * @param velocityContext Velocity上下文
     * @param codeCoreList    代码核心业务对象列表
     */
    public static void generateCode(VelocityContext velocityContext, List<GenCodeCoreBo> codeCoreList) {
        if (Objects.isNull(velocityContext) || Objects.isNull(codeCoreList)) {
            return;
        }

        for (GenCodeCoreBo codeCore : codeCoreList) {
            // 生成文件路径（解析路径模板）
            String resolvedPath = renderTemplate(velocityContext, "filePathTemplate", codeCore.getFilePath());
            // 生成文件内容（解析代码模板）
            String resolvedCode = renderTemplate(velocityContext, "codeTemplate", codeCore.getCode());

            codeCore.setFilePath(resolvedPath);
            codeCore.setCode(resolvedCode);
        }
    }

    /**
     * 使用Velocity模板引擎渲染模板内容
     *
     * @param context       Velocity上下文
     * @param templateName  模板名称（用于日志和错误提示）
     * @param templateContent 模板内容
     * @return 渲染后的字符串
     */
    private static String renderTemplate(VelocityContext context, String templateName, String templateContent) {
        if (!StringUtils.hasText(templateName) || !StringUtils.hasText(templateContent)) {
            return "";
        }

        try (StringWriter writer = new StringWriter()) {
            Velocity.evaluate(context, writer, templateName, templateContent);
            return writer.toString();
        } catch (Exception e) {
            throw new UtilException("模板渲染失败，模板名称：" + templateName, e);
        }
    }

}
