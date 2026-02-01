package com.xht.modules.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.modules.cache.TypeMappingCache;
import com.xht.modules.common.enums.DataBaseTypeEnums;
import com.xht.modules.common.enums.PageStyleEnums;
import com.xht.modules.generate.domain.bo.ColumnBo;
import com.xht.modules.generate.domain.bo.TableBo;
import com.xht.modules.generate.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.xht.modules.common.constant.GenConstant.COLUMN_NOT_FORM;

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
     * @param tableBo          表信息实体
     */
    public static GenTableEntity parseTableInfo(GenDataSourceEntity dataSourceEntity, TableBo tableBo) {
        GenTableEntity tableEntity = new GenTableEntity();
        tableEntity.setId(tableBo.getTableId());
        tableEntity.setGroupId(null);
        tableEntity.setDataSourceId(dataSourceEntity.getId());
        tableEntity.setDataBaseType(dataSourceEntity.getDbType());
        tableEntity.setEngineName(tableBo.getEngineName());
        tableEntity.setTableName(tableBo.getTableName());
        tableEntity.setTableComment(tableBo.getTableComment());
        tableEntity.setModuleName(tableBo.getModuleName());
        tableEntity.setServiceName(tableBo.getServiceName());
        tableEntity.setCodeName(tableBo.getCodeName());
        tableEntity.setCodeComment(tableBo.getCodeComment());
        tableEntity.setBackEndAuthor("xht");
        tableEntity.setFrontEndAuthor("xht");
        tableEntity.setUrlPrefix(tableBo.getUrlPrefix());
        tableEntity.setPermissionPrefix(tableBo.getPermissionPrefix());
        tableEntity.setParentMenuId(0L);
        tableEntity.setMenuIcon("dashboard");
        tableEntity.setMenuPath(tableBo.getUrlPrefix());
        tableEntity.setPageStyle(PageStyleEnums.DRAWER);
        tableEntity.setPageStyleWidth(45);
        tableEntity.setFromNumber(12);
        tableEntity.setTableCreateTime(tableBo.getTableCreateTime());
        tableEntity.setTableUpdateTime(tableBo.getTableUpdateTime());
        return tableEntity;
    }

    /**
     * 解析列信息列表，为每个列信息实体设置关联属性和扩展配置
     *
     * @param tableBo        表信息业务对象
     * @param columnInfoList 列信息业务对象
     */
    public static List<GenTableColumnEntity> parseColumnInfos(GenDataSourceEntity dataSourceEntity, TableBo tableBo, List<ColumnBo> columnInfoList) {
        if (columnInfoList == null || columnInfoList.isEmpty()) {
            log.warn("表[{}]的列信息列表为空，无需解析", tableBo.getTableName());
            return Collections.emptyList();
        }
        List<GenTableColumnEntity> columnEntityList = new ArrayList<>();
        for (ColumnBo columnBo : columnInfoList) {
            columnEntityList.add(convertColumnBoToEntity(dataSourceEntity.getDbType(), tableBo, columnBo));
        }
        return columnEntityList;
    }


    /**
     * 将列信息业务对象转换为表列实体对象
     *
     * @param tableBo  表信息业务对象
     * @param columnBo 列信息业务对象
     * @return 表列实体对象
     */
    private static GenTableColumnEntity convertColumnBoToEntity(DataBaseTypeEnums dataBaseTypeEnums, TableBo tableBo, ColumnBo columnBo) {
        GenTableColumnEntity result = new GenTableColumnEntity();
        result.setId(IdUtil.getSnowflakeNextId());
        result.setTableId(tableBo.getTableId());
        result.setTableName(tableBo.getTableName());
        result.setDbName(columnBo.getDbName());
        result.setDbType(columnBo.getDbType());
        result.setDbPrimary(columnBo.getDbPrimary());
        result.setDbRequired(columnBo.getDbRequired());
        result.setDbComment(columnBo.getDbComment());
        result.setDbLength(columnBo.getDbLength());
        result.setCodeName(columnBo.getCodeName());
        result.setCodeComment(columnBo.getCodeComment());
        result.setFromInsert(columnBo.getFromInsert());
        result.setFromUpdate(columnBo.getFromUpdate());
        result.setFromLength(columnBo.getFromLength());
        result.setFromFill(columnBo.getFromFill());
        result.setFromComponent(columnBo.getFromComponent());
        result.setListShow(columnBo.getListShow());
        result.setListDisabled(columnBo.getListDisabled());
        result.setListHidden(columnBo.getListHidden());
        result.setListSortable(columnBo.getListSortable());
        TypeMappingCache typeMappingCache = SpringContextUtils.getBean(TypeMappingCache.class);
        GenTypeMappingEntity mappingEntity = typeMappingCache.getTargetType(dataBaseTypeEnums, columnBo.getDbType());
        result.setCodeJava(StrUtil.emptyToDefault(mappingEntity.getJavaType(), "Object"));
        result.setCodeJavaPackage(mappingEntity.getImportPackage());
        String any = StrUtil.emptyToDefault(mappingEntity.getTsType(), "any");
        result.setCodeTs(any);
        result.setDictCode(null);
        result.setSortOrder(columnBo.getSortOrder());
        return result;
    }

    /**
     * 生成查询条件列表
     *
     * @param saveColumnEntity 保存列信息列表
     * @return 查询条件列表
     */
    public static List<GenTableColumnQueryEntity> genTableColumnQueryEntities(List<GenTableColumnEntity> saveColumnEntity) {
        if (CollectionUtils.isEmpty(saveColumnEntity)) {
            return Collections.emptyList();
        }
        List<GenTableColumnQueryEntity> queryEntities = new ArrayList<>(saveColumnEntity.size()); // 预设初始容量
        int sortOrder = 0;
        for (GenTableColumnEntity item : saveColumnEntity) {
            String dbName = item.getDbName();
            if (ArrayUtil.contains(COLUMN_NOT_FORM, dbName)) {
                continue;
            }
            if (StrUtil.equals(item.getCodeJava(), "String") && item.getFromLength() != null && item.getFromLength() > 0 && item.getFromLength() <= 255) {
                GenTableColumnQueryEntity queryEntity = createBaseQueryEntity(item);
                queryEntity.setQueryType("like");
                queryEntity.setSortOrder(sortOrder++);
                queryEntities.add(queryEntity);
            } else if (StrUtil.equalsAny(item.getCodeJava(), "LocalDateTime", "LocalDate", "LocalTime")) {
                String labelPrefix = StrUtil.removeAll(item.getCodeComment(), "时间");
                // 创建大于等于条件实体
                GenTableColumnQueryEntity geEntity = createBaseQueryEntity(item);
                geEntity.setConditionLabel(labelPrefix + "开始时间");
                geEntity.setQueryType("ge");
                geEntity.setConditionValue(item.getCodeName() + "Start");
                geEntity.setSortOrder(sortOrder++);
                queryEntities.add(geEntity);
                // 创建小于等于条件实体
                GenTableColumnQueryEntity leEntity = createBaseQueryEntity(item);
                leEntity.setConditionLabel(labelPrefix + "结束时间");
                leEntity.setQueryType("le");
                leEntity.setConditionValue(item.getCodeName() + "End");
                leEntity.setSortOrder(sortOrder++);
                queryEntities.add(leEntity);
            }
        }
        return queryEntities;
    }

    /**
     * 创建基础查询实体对象
     *
     * @param item 原始数据项
     * @return 基础查询实体
     */
    private static GenTableColumnQueryEntity createBaseQueryEntity(GenTableColumnEntity item) {
        GenTableColumnQueryEntity entity = new GenTableColumnQueryEntity();
        entity.setTableId(item.getTableId());
        entity.setTableName(item.getTableName());
        entity.setColumnId(item.getId());
        entity.setColumnName(item.getCodeName());
        entity.setFromLength(item.getFromLength());
        entity.setConditionValue(item.getCodeName());
        entity.setConditionLabel(item.getCodeComment());
        entity.setFromComponent(item.getFromComponent());
        entity.setDictCode(item.getDictCode());
        entity.setSortOrder(item.getSortOrder());
        return entity;
    }


}