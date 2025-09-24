package com.xht.generate.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.converter.GenTableColumnConverter;
import com.xht.generate.converter.GenTableColumnQueryConverter;
import com.xht.generate.converter.GenTableConverter;
import com.xht.generate.dao.GenDataSourceDao;
import com.xht.generate.dao.GenTableColumnDao;
import com.xht.generate.dao.GenTableColumnQueryDao;
import com.xht.generate.dao.GenTableDao;
import com.xht.generate.domain.bo.ColumnBo;
import com.xht.generate.domain.bo.TableBo;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.form.*;
import com.xht.generate.domain.query.DataBaseQueryRequest;
import com.xht.generate.domain.query.GenTableInfoQueryRequest;
import com.xht.generate.domain.response.GenTableColumnQueryResponse;
import com.xht.generate.domain.response.GenTableColumnResponse;
import com.xht.generate.domain.response.GenTableResponse;
import com.xht.generate.domain.vo.TableColumnVo;
import com.xht.generate.helper.GenInfoHelper;
import com.xht.generate.service.IGenTableService;
import com.xht.generate.strategy.IDataBaseQuery;
import com.xht.generate.utils.JDBCConfig;
import com.xht.generate.utils.JDBCUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 表信息管理Service实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class GenTableServiceImpl implements IGenTableService, InitializingBean {

    private final GenTableDao genTableDao;

    private final GenDataSourceDao genDataSourceDao;

    private final GenTableColumnDao genTableColumnDao;

    private final GenTableColumnQueryDao genTableColumnQueryDao;

    private final GenTableConverter genTableConverter;

    private final GenTableColumnConverter genTableColumnConverter;

    private final GenTableColumnQueryConverter genTableColumnQueryConverter;

    private final Map<DataBaseTypeEnums, IDataBaseQuery> queryMap = new ConcurrentHashMap<>();

    /**
     * 导入表
     *
     * @param formRequest 表信息表单请求参数
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importTable(ImportTableFormRequest formRequest) {
        List<String> tableNames = formRequest.getTableNames();
        Long dataSourceId = formRequest.getDataSourceId();
        GenDataSourceEntity dataSourceEntity = genDataSourceDao.findById(dataSourceId);
        ThrowUtils.notNull(dataSourceEntity, "数据源不存在");
        JDBCUtils jdbcUtils = null;
        try {
            JDBCConfig jdbcConfig = JDBCConfig.Builder.of(dataSourceEntity.getUrl(), dataSourceEntity.getUsername(), dataSourceEntity.getPassword(), dataSourceEntity.getDbType().getDriverClassName()).build();
            jdbcUtils = JDBCUtils.create(jdbcConfig);
            DataBaseTypeEnums dbType = dataSourceEntity.getDbType();
            IDataBaseQuery dataBaseQuery = queryMap.get(dbType);
            ThrowUtils.notNull(dataBaseQuery, String.format("暂不支持该数据类型：%s", dbType));
            JdbcTemplate jdbcTemplate = jdbcUtils.getJdbcTemplate();
            List<GenTableEntity> saveTableEntity = new ArrayList<>();
            List<GenTableColumnEntity> saveColumnEntity = new ArrayList<>();
            for (String tableName : tableNames) {
                TableBo tableBo = dataBaseQuery.selectTableByTableName(jdbcTemplate, tableName);
                if (Objects.nonNull(tableBo)) {
                    tableBo.setTableId(IdUtil.getSnowflakeNextId());
                    GenTableEntity tableEntity = GenInfoHelper.parseTableInfo(dataSourceEntity, tableBo);
                    tableEntity.setGroupId(formRequest.getGroupId());
                    saveTableEntity.add(tableEntity);
                    List<ColumnBo> columnBoList = dataBaseQuery.selectTableColumnsByTableName(jdbcTemplate, tableName);
                    saveColumnEntity.addAll(GenInfoHelper.parseColumnInfos(dataSourceEntity, tableBo, columnBoList));
                }
            }
            if (!CollectionUtils.isEmpty(saveTableEntity)) {
                genTableDao.saveAll(saveTableEntity);
            }
            if (!CollectionUtils.isEmpty(saveColumnEntity)) {
                genTableColumnDao.saveAll(saveColumnEntity);
            }
        } catch (Exception e) {
            log.error("导入失败 {}", e.getMessage(), e);
            throw new BusinessException("导入失败");
        } finally {
            if (Objects.nonNull(jdbcUtils)) {
                jdbcUtils.close();
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 同步表信息
     *
     * @param tableId 表id
     * @return 操作结果 true表示成功，false表示失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncTable(Long tableId) {
        GenTableEntity dbTableInfoEntity = genTableDao.findById(tableId);
        ThrowUtils.notNull(dbTableInfoEntity, "数据不存在");
        String tableName = dbTableInfoEntity.getTableName();
        GenDataSourceEntity dataSourceEntity = genDataSourceDao.findById(dbTableInfoEntity.getDataSourceId());
        ThrowUtils.notNull(dataSourceEntity, "数据源不存在");
        JDBCUtils jdbcUtils = null;
        try {
            JDBCConfig jdbcConfig = JDBCConfig.Builder.of(dataSourceEntity).build();
            jdbcUtils = JDBCUtils.create(jdbcConfig);
            DataBaseTypeEnums dbType = dataSourceEntity.getDbType();
            IDataBaseQuery dataBaseQuery = queryMap.get(dbType);
            ThrowUtils.notNull(dataBaseQuery, String.format("暂不支持该数据类型：%s", dbType));
            JdbcTemplate jdbcTemplate = jdbcUtils.getJdbcTemplate();
            TableBo tableBo = dataBaseQuery.selectTableByTableName(jdbcTemplate, tableName);
            ThrowUtils.notNull(tableBo, String.format("表`%s`不存在", tableName));
            GenTableEntity tableEntity = GenInfoHelper.parseTableInfo(dataSourceEntity, tableBo);
            tableEntity.setId(tableId);
            tableBo.setTableId(tableId);
            List<ColumnBo> genColumnInfoEntities = dataBaseQuery.selectTableColumnsByTableName(jdbcTemplate, tableName);
            genTableColumnDao.deleteByTableId(tableId);
            genTableDao.updateById(tableEntity);
            genTableColumnDao.saveAll(GenInfoHelper.parseColumnInfos(dataSourceEntity, tableBo, genColumnInfoEntities));
        } catch (Exception e) {
            log.error("数据库连接失败 {}", e.getMessage(), e);
            throw new BusinessException("数据库连接失败");
        } finally {
            if (Objects.nonNull(jdbcUtils)) {
                jdbcUtils.close();
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 根据ID删除表信息
     *
     * @param tableId 表信息ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeById(Long tableId) {
        genTableDao.deleteById(tableId);
        genTableColumnDao.deleteByTableId(tableId);
        return Boolean.TRUE;
    }

    /**
     * 根据ID更新表信息
     *
     * @param formRequest 表信息更新请求参数
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateById(TableColumnForm formRequest) {
        GenTableInfoFormRequest tableInfo = formRequest.getTableInfo();
        List<GenColumnInfoFormRequest> columns = formRequest.getColumnInfos();
        List<GenTableColumnQueryFormRequest> queryColumns = formRequest.getQueryColumns();
        ThrowUtils.notEmpty(columns, "字段信息不能为空");
        Boolean menuExists = genTableDao.exists(GenTableEntity::getId, tableInfo.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "表信息不存在");
        for (GenColumnInfoFormRequest column : columns) {
            genTableColumnDao.updateFormRequest(column);
        }
        genTableColumnQueryDao.deleteByTableId(tableInfo.getId());
        if (!CollectionUtils.isEmpty(queryColumns)){
            genTableColumnQueryDao.saveAll(genTableColumnQueryConverter.toEntity(queryColumns));
        }
        return genTableDao.updateFormRequest(formRequest.getTableInfo());
    }

    /**
     * 根据ID查询表信息
     *
     * @param tableId 表信息ID
     * @return 表信息字段信息
     */
    @Override
    public TableColumnVo getById(Long tableId) {
        TableColumnVo result = new TableColumnVo();
        GenTableResponse tableResponse = genTableConverter.toResponse(genTableDao.findById(tableId));
        result.setTableInfo(Objects.requireNonNullElseGet(tableResponse, GenTableResponse::new));
        List<GenTableColumnResponse> columnResponses = genTableColumnConverter.toResponse(genTableColumnDao.findByTableId(tableId));
        result.setColumnInfos(Objects.requireNonNullElseGet(columnResponses, Collections::emptyList));
        List<GenTableColumnQueryResponse> queryResponseList = genTableColumnQueryConverter.toResponse(genTableColumnQueryDao.findByTableId(tableId));
        result.setQueryColumns(Objects.requireNonNullElseGet(queryResponseList, Collections::emptyList));
        return result;
    }


    /**
     * 分页查询已存在的表信息
     *
     * @param queryRequest 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Override
    public PageResponse<GenTableResponse> selectExistsPage(GenTableInfoQueryRequest queryRequest) {
        Page<GenTableEntity> page = genTableDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genTableConverter.toResponse(page);
    }

    /**
     * 分页查询不存在的表信息
     *
     * @param queryRequest 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Override
    public List<GenTableResponse> selectNoExistsList(DataBaseQueryRequest queryRequest) {
        String tableName = queryRequest.getTableName();
        Long dataSourceId = queryRequest.getDataSourceId();
        GenDataSourceEntity dataSourceEntity = genDataSourceDao.findById(dataSourceId);
        ThrowUtils.notNull(dataSourceEntity, "数据源不存在");
        JDBCUtils jdbcUtils = null;
        try {
            JDBCConfig jdbcConfig = JDBCConfig.Builder.of(dataSourceEntity).build();
            jdbcUtils = JDBCUtils.create(jdbcConfig);
            DataBaseTypeEnums dbType = dataSourceEntity.getDbType();
            IDataBaseQuery dataBaseQuery = queryMap.get(dbType);
            ThrowUtils.notNull(dataBaseQuery, String.format("暂不支持该数据类型：%s", dbType));
            List<TableBo> tableBoList = dataBaseQuery.selectListTableByLike(jdbcUtils.getJdbcTemplate(), tableName);
            if (CollectionUtils.isEmpty(tableBoList)) {
                return Collections.emptyList();
            }
            List<String> tableNames = genTableDao.findTableNameByDbId(dataSourceId);
            if (CollectionUtils.isEmpty(tableNames)) {
                return genTableConverter.toListResponse(tableBoList);
            }
            return genTableConverter.toListResponse(tableBoList.stream().filter(item -> !tableNames.contains(item.getTableName())).toList());
        } catch (Exception e) {
            log.error("数据库连接失败 {}", e.getMessage(), e);
            throw new BusinessException("数据库连接失败");
        } finally {
            if (Objects.nonNull(jdbcUtils)) {
                jdbcUtils.close();
            }
        }
    }


    @Override
    public void afterPropertiesSet() {
        Map<String, IDataBaseQuery> beansOfType = SpringContextUtils.getBeansOfType(IDataBaseQuery.class);
        for (IDataBaseQuery value : beansOfType.values()) {
            queryMap.put(value.support(), value);
        }
    }
}
