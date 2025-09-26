package com.xht.generate.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
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
     * 生成代码文件
     *
     * @param request      代码生成核心请求参数
     * @param table        表信息实体
     * @param tableColumns 表列信息实体列表
     * @param codeCore     解析后的代码核心业务对象列表
     */
    public static List<GenCodeCoreBo> generateCode(GenCodeCoreRequest request, GenTableEntity table, List<GenTableColumnEntity> tableColumns, List<GenCodeCoreBo> codeCore) {
        List<GenCodeCoreBo> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(codeCore)) {
            return Collections.emptyList();
        }
        VelocityContext context = new VelocityContext();
        context.put("packageName", request.getPackageName());
        context.put("nowDate", DateUtil.now());
        context.put("tableId", table.getId());
        context.put("groupId", table.getGroupId());
        context.put("dataSourceId", table.getDataSourceId());
        context.put("dataBaseType", table.getDataBaseType());
        context.put("engineName", table.getEngineName());
        context.put("tableName", table.getTableName());
        context.put("tableComment", table.getTableComment());
        context.put("moduleName", table.getModuleName());
        context.put("serviceName", table.getServiceName());
        context.put("codeName", StrUtil.lowerFirst(table.getCodeName()));
        context.put("codeNameUpperFirst", StrUtil.upperFirst(table.getCodeName()));
        context.put("codeComment", table.getCodeComment());
        context.put("backEndAuthor", table.getBackEndAuthor());
        context.put("frontEndAuthor", table.getFrontEndAuthor());
        context.put("urlPrefix", table.getUrlPrefix());
        context.put("permissionPrefix", table.getPermissionPrefix());
        context.put("parentMenuId", table.getParentMenuId());
        context.put("pageStyle", table.getPageStyle());
        context.put("pageStyleWidth", table.getPageStyleWidth());
        context.put("fromNumber", table.getFromNumber());
        Set<String> allPackages = new HashSet<>();
        context.put("allColumns", convertColumnToMap(tableColumns, allPackages));
        context.put("allPackages", allPackages);
        context.put("pkColumn", filterPkColumnToMap(tableColumns));
        addIdGenerators(context);
        for (GenCodeCoreBo item : codeCore) {
            List<GenTableColumnEntity> filter = item.filter(tableColumns);
            Set<String> importClassNames = new HashSet<>();
            context.put("column", convertColumnToMap(filter, importClassNames));
            context.put("packages", importClassNames);
            // 渲染文件路径
            String resolvedPath = renderTemplate(context, "filePathTemplate", item.getFilePath());
            // 渲染文件内容
            String resolvedCode = renderTemplate(context, "codeTemplate", item.getCode());
            GenCodeCoreBo genCodeCoreBo = new GenCodeCoreBo(item.getIgnoreField());
            genCodeCoreBo.setFilePath(resolvedPath);
            genCodeCoreBo.setCode(resolvedCode);
            result.add(genCodeCoreBo);
        }
        return result;
    }


    /**
     * 将表列信息实体列表转换为Map列表
     *
     * @param tableColumns 表列信息实体列表
     * @return 转换后的Map列表，每个Map代表一列的信息
     */
    private static List<Map<String, Object>> convertColumnToMap(List<GenTableColumnEntity> tableColumns, Set<String> importClassNames) {
        List<Map<String, Object>> columnMaps = new ArrayList<>();
        for (GenTableColumnEntity tableColumn : tableColumns) {
            columnMaps.add(convertColumnToMap(tableColumn));
            if (StringUtils.hasText(tableColumn.getCodeJavaPackage())) {
                importClassNames.add(tableColumn.getFromComponent());
            }
        }
        return columnMaps;
    }

    /**
     * 将表列信息实体转换为Map
     *
     * @param tableColumn 表列信息实体
     * @return 转换后的Map
     */
    private static Map<String, Object> convertColumnToMap(GenTableColumnEntity tableColumn) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("id", tableColumn.getId());
        columnMap.put("dbName", tableColumn.getDbName());
        columnMap.put("dbType", tableColumn.getDbType());
        columnMap.put("dbPrimary", tableColumn.getDbPrimary().getValue());
        columnMap.put("dbRequired", tableColumn.getDbRequired().getValue());
        columnMap.put("dbComment", tableColumn.getDbComment());
        columnMap.put("dbLength", tableColumn.getDbLength());
        columnMap.put("codeName", StrUtil.lowerFirst(tableColumn.getCodeName()));
        columnMap.put("codeNameUpperFirst", StrUtil.upperFirst(tableColumn.getCodeName()));
        columnMap.put("codeComment", tableColumn.getCodeComment());
        columnMap.put("fromInsert", tableColumn.getFromInsert().getValue());
        columnMap.put("fromUpdate", tableColumn.getFromUpdate().getValue());
        columnMap.put("fromLength", tableColumn.getFromLength());
        columnMap.put("fromFill", tableColumn.getFromFill().getValue());
        columnMap.put("fromComponent", tableColumn.getFromComponent());
        columnMap.put("listShow", tableColumn.getListShow().getValue());
        columnMap.put("listComment", tableColumn.getListComment());
        columnMap.put("listDisabled", tableColumn.getListDisabled().getValue());
        columnMap.put("listHidden", tableColumn.getListHidden().getValue());
        columnMap.put("codeJava", tableColumn.getCodeJava());
        columnMap.put("codeJavaPackage", tableColumn.getCodeJavaPackage());
        columnMap.put("codeTs", tableColumn.getCodeTs());
        columnMap.put("sortOrder", tableColumn.getSortOrder());
        return columnMap;
    }

    /**
     * 从表列信息列表中筛选主键列并转换为Map
     *
     * @param tableColumns 表列信息实体列表
     * @return 主键列信息转换后的Map，如果没有找到主键列则返回空实体转换的Map
     */
    private static Map<String, Object> filterPkColumnToMap(List<GenTableColumnEntity> tableColumns) {
        GenTableColumnEntity pkColumn = tableColumns.stream()
                .filter(col -> IdPrimaryKeyEnums.YES.equals(col.getDbPrimary()))
                .findFirst()
                .orElse(new GenTableColumnEntity());
        return convertColumnToMap(pkColumn);
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
                .map((template) -> {
                    GenCodeCoreBo genCodeCoreBo = new GenCodeCoreBo(template.getTemplateIgnoreField());
                    genCodeCoreBo.setFilePath(String.format("%s%s.%s", template.getTemplateFilePath(), template.getTemplateFileName(), template.getTemplateFileType()));
                    genCodeCoreBo.setCode(template.getTemplateContent());
                    return genCodeCoreBo;
                })
                .collect(Collectors.toList());
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
