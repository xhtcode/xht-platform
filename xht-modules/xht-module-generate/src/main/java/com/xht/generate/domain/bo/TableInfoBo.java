package com.xht.generate.domain.bo;

import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.entity.GenTableColumnQueryEntity;
import com.xht.generate.domain.entity.GenTableEntity;
import org.apache.velocity.VelocityContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 表信息业务对象
 *
 * @author xht
 **/
public final class TableInfoBo {

    /**
     * 表信息
     */
    private final GenTableEntity table;

    /**
     * 主键字段信息
     */
    private GenTableColumnEntity pkColumn;

    /**
     * 全部字段列表信息
     */
    private final List<GenTableColumnEntity> allColumns;

    /**
     * 全部字段列表 package 包信息
     */
    private final Set<String> allColumnsPackage;

    /**
     * 查询字段列表信息
     */
    private final List<ColumnQueryBo> queryColumns = new ArrayList<>();

    /**
     * 查询字段列表 package 包信息
     */
    private final Set<String> queryColumnsPackage = new HashSet<>();

    /**
     * 字段列表信息
     */
    private final List<GenTableColumnEntity> columns = new ArrayList<>();

    /**
     * 字段列表 package 包信息
     */
    private final Set<String> packages = new HashSet<>();

    /**
     * 表单 字段列表信息
     */
    private final List<GenTableColumnEntity> formColumns = new ArrayList<>();

    /**
     * 表单 字段列表 package 包信息
     */
    private final Set<String> formColumnsPackage = new HashSet<>();


    /**
     * 列表 字段列表信息
     */
    private final List<GenTableColumnEntity> listColumns = new ArrayList<>();

    /**
     * 列表 字段列表 package 包信息
     */
    private final Set<String> listColumnsPackage = new HashSet<>();


    /**
     * 表单 字段列表信息
     */
    private final List<GenTableColumnEntity> formListDifferenceColumns = new ArrayList<>();

    /**
     * 构造表信息业务对象
     *
     * @param table        表实体对象，不能为空
     * @param columns      表列实体列表，不能为空
     * @param queryColumns 查询列实体列表，不能为空
     */
    public TableInfoBo(GenTableEntity table, List<GenTableColumnEntity> columns, List<GenTableColumnQueryEntity> queryColumns) {
        ThrowUtils.notNull(table, "table must not be null");
        ThrowUtils.notNull(columns, "columns must not be null");
        ThrowUtils.notNull(queryColumns, "queryColumns must not be null");
        Map<Long, GenTableColumnEntity> allColumnsById = new HashMap<>();
        for (GenTableColumnEntity column : columns) {
            allColumnsById.put(column.getId(), column);
            if (Objects.equals(column.getDbPrimary(), IdPrimaryKeyEnums.YES)) {
                this.pkColumn = column;
            }
        }
        this.table = table;
        this.allColumns = columns;
        this.allColumnsPackage = columns.stream().map(GenTableColumnEntity::getCodeJavaPackage).collect(Collectors.toSet());
        for (GenTableColumnQueryEntity queryColumn : queryColumns) {
            initQueryColumns(queryColumn, allColumnsById.get(queryColumn.getColumnId()));
        }
    }

    /**
     * 初始化查询字段信息
     *
     * @param queryColumns 查询字段信息
     * @param column       表列信息
     */
    private void initQueryColumns(GenTableColumnQueryEntity queryColumns, GenTableColumnEntity column) {
        ColumnQueryBo columnQueryBo = new ColumnQueryBo();
        columnQueryBo.setTableId(queryColumns.getTableId());
        columnQueryBo.setTableName(queryColumns.getTableName());
        columnQueryBo.setColumnId(queryColumns.getColumnId());
        columnQueryBo.setColumnName(queryColumns.getColumnName());
        columnQueryBo.setFromLength(queryColumns.getFromLength());
        columnQueryBo.setQueryType(queryColumns.getQueryType());
        columnQueryBo.setConditionLabel(queryColumns.getConditionLabel());
        columnQueryBo.setConditionValue(queryColumns.getConditionValue());
        columnQueryBo.setFromComponent(queryColumns.getFromComponent());
        columnQueryBo.setDictCode(queryColumns.getDictCode());
        columnQueryBo.setDictStatus(StringUtils.hasText(queryColumns.getDictCode()));
        columnQueryBo.setDbName(column.getDbName());
        columnQueryBo.setDbType(column.getDbType());
        columnQueryBo.setDbComment(column.getDbComment());
        columnQueryBo.setDbLength(column.getDbLength());
        columnQueryBo.setCodeName(column.getCodeName());
        columnQueryBo.setCodeNameUpperFirst(StrUtil.upperFirst(column.getCodeName()));
        columnQueryBo.setCodeComment(column.getCodeComment());
        columnQueryBo.setCodeJava(column.getCodeJava());
        columnQueryBo.setCodeJavaPackage(column.getCodeJavaPackage());
        columnQueryBo.setCodeTs(column.getCodeTs());
        this.queryColumns.add(columnQueryBo);
        if (StringUtils.hasText(column.getCodeJavaPackage())) {
            this.queryColumnsPackage.add(column.getCodeJavaPackage());
        }
    }

    /**
     * 将表列信息实体列表转换为Map列表
     *
     * @param tableColumns 表列信息实体列表
     * @return 转换后的Map列表，每个Map代表一列的信息
     */
    private List<Map<String, Object>> convertColumnToMap(List<GenTableColumnEntity> tableColumns) {
        return tableColumns.stream().map(this::convertColumnToMap).collect(Collectors.toList());
    }

    /**
     * 将表列信息实体转换为Map
     *
     * @param tableColumn 表列信息实体
     * @return 转换后的Map
     */
    private Map<String, Object> convertColumnToMap(GenTableColumnEntity tableColumn) {
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
        columnMap.put("listDisabled", Objects.equals(GenStatusEnums.YES, tableColumn.getListDisabled()));
        columnMap.put("listHidden", Objects.equals(GenStatusEnums.YES, tableColumn.getListHidden()));
        columnMap.put("codeJava", tableColumn.getCodeJava());
        columnMap.put("codeJavaPackage", tableColumn.getCodeJavaPackage());
        columnMap.put("codeTs", tableColumn.getCodeTs());
        columnMap.put("dictCode", tableColumn.getDictCode());
        columnMap.put("dictStatus", StringUtils.hasText(tableColumn.getDictCode()));
        columnMap.put("sortOrder", tableColumn.getSortOrder());
        return columnMap;
    }

    /**
     * 填充 velocity 模板上下文
     *
     * @param context velocity 模板上下文
     */
    public void fillVelocityContext(VelocityContext context, Set<String> ignoreField) {
        this.columns.clear();
        this.packages.clear();
        this.formColumns.clear();
        this.formColumnsPackage.clear();
        this.listColumns.clear();
        this.listColumnsPackage.clear();
        this.formListDifferenceColumns.clear();
        for (GenTableColumnEntity column : this.allColumns) {
            if (ignoreField.contains(StrUtil.toLowerCase(column.getCodeName()))) {
                continue;
            }
            this.columns.add(column);
            if (StringUtils.isEmpty(column.getCodeJavaPackage())) {
                this.packages.add(column.getCodeJavaPackage());
            }
            if (Objects.equals(column.getFromInsert(), GenStatusEnums.YES) || Objects.equals(column.getFromUpdate(), GenStatusEnums.YES)) {
                this.formColumns.add(column);
                if (StringUtils.isEmpty(column.getCodeJavaPackage())) {
                    this.formColumnsPackage.add(column.getCodeJavaPackage());
                }
                if (!Objects.equals(column.getListShow(), GenStatusEnums.YES) && Objects.equals(column.getDbPrimary(), IdPrimaryKeyEnums.NO)) {
                    this.formListDifferenceColumns.add(column);
                }
            }
            if (Objects.equals(column.getListShow(), GenStatusEnums.YES)) {
                this.listColumns.add(column);
                if (StringUtils.isEmpty(column.getCodeJavaPackage())) {
                    this.listColumnsPackage.add(column.getCodeJavaPackage());
                }
                if (!(Objects.equals(column.getFromInsert(), GenStatusEnums.YES) || Objects.equals(column.getFromUpdate(), GenStatusEnums.YES)) && Objects.equals(column.getDbPrimary(), IdPrimaryKeyEnums.NO)) {
                    this.formListDifferenceColumns.add(column);
                }
            }
        }

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
        context.put("pageStyle", table.getPageStyle().getValue());
        context.put("pageStyleWidth", table.getPageStyleWidth());
        context.put("fromNumber", table.getFromNumber());
        context.put("pkColumn", convertColumnToMap(this.pkColumn));
        context.put("allColumns", convertColumnToMap(this.allColumns));
        context.put("allColumnsPackage", this.allColumnsPackage);
        context.put("columns", convertColumnToMap(this.columns));
        context.put("packages", this.packages);
        context.put("queryColumns", this.queryColumns);
        context.put("queryColumnsPackage", this.queryColumnsPackage);
        context.put("formColumns", convertColumnToMap(this.formColumns));
        context.put("formColumnsPackage", this.formColumnsPackage);
        context.put("listColumns", convertColumnToMap(this.listColumns));
        context.put("listColumnsPackage", this.listColumnsPackage);
        context.put("formListDifferenceColumns", convertColumnToMap(this.formListDifferenceColumns));
    }


    /**
     * 获取表名
     *
     * @return 表名
     */
    public String getTableName() {
        return this.table.getTableName();
    }
}
