package com.xht.modules.helper;

import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.modules.cache.TypeMappingCache;
import com.xht.modules.common.enums.DataBaseTypeEnums;
import com.xht.modules.common.enums.PageStyleEnums;
import com.xht.modules.generate.domain.bo.ColumnBo;
import com.xht.modules.generate.domain.bo.TableBo;
import com.xht.modules.generate.domain.entity.GenDataSourceEntity;
import com.xht.modules.generate.domain.entity.GenTableColumnEntity;
import com.xht.modules.generate.domain.entity.GenTableEntity;
import com.xht.modules.generate.domain.entity.GenTypeMappingEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
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
        tableEntity.setPageStyle(PageStyleEnums.DRAWER);
        tableEntity.setPageStyleWidth(45);
        tableEntity.setFromNumber(4);
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
        result.setId(null);
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
        TypeMappingCache typeMappingCache = SpringContextUtils.getBean(TypeMappingCache.class);
        GenTypeMappingEntity mappingEntity = typeMappingCache.getTargetType(dataBaseTypeEnums, columnBo.getDbType());
        result.setCodeJava(StrUtil.emptyToDefault(mappingEntity.getJavaType(), "Object"));
        result.setCodeJavaPackage(mappingEntity.getImportPackage());
        result.setCodeTs(StrUtil.emptyToDefault(mappingEntity.getTsType(), "any"));
        result.setDictCode(null);
        result.setSortOrder(columnBo.getSortOrder());
        return result;
    }

}