package com.xht.generate.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.generate.constant.GenConstant;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.domain.ColumnExtConfig;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 生成信息辅助类
 * 负责处理代码生成过程中的表和列信息解析与转换
 *
 * @author xht
 **/
@Slf4j
public final class GenInfoHelper {

    /**
     * 私有构造方法，防止实例化工具类
     */
    private GenInfoHelper() {
        throw new AssertionError("禁止实例化工具类");
    }

    /**
     * 解析数据源和表信息，为表信息实体设置唯一ID和基本属性
     *
     * @param dataSourceEntity 数据源实体
     * @param tableInfoEntity  表信息实体
     */
    public static void parseTableInfo(GenDataSourceEntity dataSourceEntity, GenTableInfoEntity tableInfoEntity) {
        // 生成雪花算法ID
        if (!StringUtils.hasText(tableInfoEntity.getId())) {
            String tableId = IdUtil.getSnowflakeNextIdStr();
            tableInfoEntity.setId(tableId);
        }
        tableInfoEntity.setDataSourceId(dataSourceEntity.getId());
        // 格式化表名为驼峰命名（首字母大写）
        tableInfoEntity.setCodeName(formatToCamelCase(tableInfoEntity.getTableName()));
        tableInfoEntity.setCodeComment(tableInfoEntity.getTableComment());
    }


    /**
     * 解析列信息列表，为每个列信息实体设置关联属性和扩展配置
     *
     * @param tableInfoEntity    表信息实体
     * @param columnInfoEntities 列信息实体列表
     */
    public static void parseColumnInfos(GenTableInfoEntity tableInfoEntity, List<GenColumnInfoEntity> columnInfoEntities) {
        if (columnInfoEntities == null || columnInfoEntities.isEmpty()) {
            log.warn("列信息列表为空，无需解析");
            return;
        }

        for (GenColumnInfoEntity column : columnInfoEntities) {
            column.setTableId(tableInfoEntity.getId());
            // 格式化列名为驼峰命名（首字母大写）
            column.setCodeName(formatToCamelCase(column.getColumnName()));
            column.setCodeComment(column.getColumnComment());
            // 生成列扩展配置
            column.setExtConfig(buildColumnExtConfig(column));
        }
    }


    /**
     * 格式化名称为驼峰命名（首字母大写）
     *
     * @param name 需要格式化的名称（通常是数据库表名或列名）
     * @return 格式化后的驼峰命名
     */
    private static String formatToCamelCase(String name) {
        if (StrUtil.isBlank(name)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.upperFirst(StrUtil.toCamelCase(name));
    }

    /**
     * 根据列信息实体生成列扩展配置
     *
     * @param columnInfoEntity 列信息实体
     * @return 列扩展配置对象
     */
    private static ColumnExtConfig buildColumnExtConfig(GenColumnInfoEntity columnInfoEntity) {
        ColumnExtConfig extConfig = new ColumnExtConfig();
        String columnName = columnInfoEntity.getColumnName();

        // 处理表单项相关配置
        GenStatusEnums isFormItem = determineStatus(GenConstant.COLUMN_NOT_FORM, columnName);
        extConfig.setFormItem(isFormItem);
        extConfig.setFormRequired(isFormItem);
        extConfig.setFormValidator(isFormItem);

        // 如果是表单项，默认设置为输入框类型
        if (GenStatusEnums.YES.equals(isFormItem)) {
            extConfig.setFormType(GenConstant.INPUT);
        }

        // 处理列表相关配置
        GenStatusEnums isGridItem = determineStatus(GenConstant.COLUMN_NOT_LIST, columnName);
        extConfig.setGridItem(isGridItem);
        extConfig.setGridSort(isGridItem);

        // 处理查询项相关配置
        GenStatusEnums isQueryItem = determineStatus(GenConstant.COLUMN_NOT_QUERY, columnName);
        extConfig.setQueryItem(isQueryItem);

        // 如果是查询项，默认设置查询方式和表单类型
        if (GenStatusEnums.YES.equals(isQueryItem)) {
            extConfig.setQueryType(GenConstant.EQ);
            extConfig.setQueryFormType(GenConstant.INPUT);
        }

        return extConfig;
    }

    /**
     * 根据列名和排除数组判断列的状态（是否包含在特定功能中）
     *
     * @param excludedColumns 排除的列名数组
     * @param columnName      列名
     * @return 如果列名在排除数组中则返回NO，否则返回YES
     */
    private static GenStatusEnums determineStatus(String[] excludedColumns, String columnName) {
        if (StrUtil.isBlank(columnName) || ArrayUtil.isEmpty(excludedColumns)) {
            return GenStatusEnums.NO;
        }

        return ArrayUtil.contains(excludedColumns, columnName)
                ? GenStatusEnums.NO
                : GenStatusEnums.YES;
    }
}
