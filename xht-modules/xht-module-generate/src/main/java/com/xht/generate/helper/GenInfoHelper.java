package com.xht.generate.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.generate.constant.GenConstant;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.domain.ColumnExtConfig;
import com.xht.generate.domain.TableExtConfig;
import com.xht.generate.domain.bo.ColumnBo;
import com.xht.generate.domain.bo.TableBo;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.entity.GenTableEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

import static com.xht.framework.core.constant.StringConstant.HORIZONTAL;
import static com.xht.framework.core.constant.StringConstant.UNDERLINE;

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
     * @param tableBo  表信息实体
     */
    public static GenTableEntity parseTableInfo(GenDataSourceEntity dataSourceEntity, TableBo tableBo) {
        GenTableEntity tableEntity = new GenTableEntity();

        return tableEntity;
    }

    /**
     * 解析列信息列表，为每个列信息实体设置关联属性和扩展配置
     *
     * @param tableBo   表信息实体
     * @param columnInfoList    列信息实体列表
     */
    public static List<GenTableColumnEntity> parseColumnInfos(TableBo tableBo, List<ColumnBo> columnInfoList) {
        if (columnInfoList == null || columnInfoList.isEmpty()) {
            log.warn("表[{}]的列信息列表为空，无需解析", tableBo.getTableName());
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    /**
     * 格式化名称为驼峰命名（首字母大写）
     * 处理逻辑：先将下划线命名转为驼峰命名，再将首字母大写
     *
     * @param name 需要格式化的名称（通常是表名或列名）
     * @return 格式化后的驼峰命名，若输入为空则返回空字符串
     */
    private static String formatToCamelCase(String name) {
        if (StrUtil.isBlank(name)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.upperFirst(StrUtil.toCamelCase(name));
    }

    /**
     * 构建表扩展配置
     *
     * @param tableName  表名
     * @param moduleName 模块名称
     * @return 表扩展配置对象
     */
    private static TableExtConfig buildTableExtConfig(String tableName, String moduleName) {
        TableExtConfig extConfig = new TableExtConfig();
        extConfig.setUrl(getPathUrl(tableName));
        extConfig.setModuleName(moduleName);
        extConfig.setServiceName(getServiceName(tableName));
        extConfig.setAuthorizationPrefix(getAuthorizationPrefix(tableName));
        return extConfig;
    }


    /**
     * 处理表单项配置
     */
    private static void handleFormConfig(ColumnExtConfig extConfig, GenStatusEnums isFormItem) {
        extConfig.setFormItem(isFormItem);
        extConfig.setFormRequired(isFormItem);
        extConfig.setFormValidator(isFormItem);

        // 若为表单项，默认设置为输入框类型
        if (GenStatusEnums.YES.equals(isFormItem)) {
            extConfig.setFormType(GenConstant.INPUT);
        }
    }

    /**
     * 处理列表项配置
     */
    private static void handleGridConfig(ColumnExtConfig extConfig, GenStatusEnums isGridItem) {
        extConfig.setList(isGridItem);
        extConfig.setListSort(isGridItem);
    }

    /**
     * 处理查询项配置
     */
    private static void handleQueryConfig(ColumnExtConfig extConfig, GenStatusEnums isQueryItem) {
        extConfig.setQueryItem(isQueryItem);

        // 若为查询项，默认设置查询方式和表单类型
        if (GenStatusEnums.YES.equals(isQueryItem)) {
            extConfig.setQueryType(GenConstant.EQ);
            extConfig.setQueryFormType(GenConstant.INPUT);
        }
    }

    /**
     * 判断列是否包含在特定功能中（是否未被排除）
     *
     * @param excludedColumns 排除的列名数组
     * @param columnName      列名
     * @return 若列名不在排除数组中则返回YES，否则返回NO
     */
    private static GenStatusEnums determineIncluded(String[] excludedColumns, String columnName) {
        if (StrUtil.isBlank(columnName) || ArrayUtil.isEmpty(excludedColumns)) {
            return GenStatusEnums.NO;
        }
        return ArrayUtil.contains(excludedColumns, columnName)
                ? GenStatusEnums.NO
                : GenStatusEnums.YES;
    }

    /**
     * 获取Controller地址前缀
     *
     * @param tableName 表名
     * @return controller地址前缀（以斜杠开头）
     */
    public static String getPathUrl(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            return StringConstant.SEPARATOR_SLASH;
        }
        String url = StrUtil.replace(tableName, UNDERLINE, StringConstant.SEPARATOR_SLASH);
        return StrUtil.addPrefixIfNot(url, StringConstant.SEPARATOR_SLASH);
    }

    /**
     * 获取权限前缀
     * 转换规则：将表名中的第一个下划线替换为冒号，其余下划线替换为短横线
     *
     * @param tableName 表名
     * @return 权限前缀字符串
     */
    public static String getAuthorizationPrefix(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            return StrUtil.EMPTY;
        }
        String replacedFirst = StrUtil.replaceFirst(tableName, UNDERLINE, ":");
        return StrUtil.replace(replacedFirst, UNDERLINE, HORIZONTAL);
    }

    /**
     * 获取业务服务名称
     * 规则：取表名中第一个下划线前的部分（若没有下划线则取整个表名）
     *
     * @param tableName 表名称
     * @return 业务服务名称
     */
    private static String getServiceName(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            return StrUtil.EMPTY;
        }
        int firstUnderlineIndex = tableName.indexOf(UNDERLINE);
        return firstUnderlineIndex > 0
                ? StrUtil.sub(tableName, 0, firstUnderlineIndex)
                : tableName;
    }
}