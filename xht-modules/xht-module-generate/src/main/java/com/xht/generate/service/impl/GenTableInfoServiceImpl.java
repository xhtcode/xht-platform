package com.xht.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.converter.GenColumnInfoConverter;
import com.xht.generate.converter.GenTableInfoConverter;
import com.xht.generate.dao.GenColumnInfoDao;
import com.xht.generate.dao.GenDataSourceDao;
import com.xht.generate.dao.GenTableInfoDao;
import com.xht.generate.domain.TableExtConfig;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.*;
import com.xht.generate.domain.response.GenTableInfoResponse;
import com.xht.generate.domain.vo.GenTableColumnVo;
import com.xht.generate.helper.GenInfoHelper;
import com.xht.generate.service.IGenTableInfoService;
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
public class GenTableInfoServiceImpl implements IGenTableInfoService, InitializingBean {

    private final GenTableInfoDao genTableInfoDao;

    private final GenDataSourceDao genDataSourceDao;

    private final GenColumnInfoDao genColumnInfoDao;

    private final GenTableInfoConverter genTableInfoConverter;

    private final GenColumnInfoConverter genColumnInfoConverter;

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
            JDBCConfig jdbcConfig = JDBCConfig.Builder.of(dataSourceEntity.getUrl(), dataSourceEntity.getName(), dataSourceEntity.getName(), dataSourceEntity.getDbType().getDriverClassName()).build();
            jdbcUtils = JDBCUtils.create(jdbcConfig);
            DataBaseTypeEnums dbType = dataSourceEntity.getDbType();
            IDataBaseQuery dataBaseQuery = queryMap.get(dbType);
            ThrowUtils.notNull(dataBaseQuery, String.format("暂不支持该数据类型：%s", dbType));
            JdbcTemplate jdbcTemplate = jdbcUtils.getJdbcTemplate();
            List<GenTableInfoEntity> saveTableEntity = new ArrayList<>();
            List<GenColumnInfoEntity> saveColumnEntity = new ArrayList<>();
            for (String tableName : tableNames) {
                GenTableInfoEntity tableInfoEntity = dataBaseQuery.selectTableByTableName(jdbcTemplate, tableName);
                if (Objects.nonNull(tableInfoEntity)) {
                    GenInfoHelper.parseTableInfo(dataSourceEntity, tableInfoEntity, formRequest.getModuleName());
                    saveTableEntity.add(tableInfoEntity);
                    List<GenColumnInfoEntity> genColumnInfoEntities = dataBaseQuery.selectTableColumnsByTableName(jdbcTemplate, tableName);
                    GenInfoHelper.parseColumnInfos(tableInfoEntity, genColumnInfoEntities);
                    saveColumnEntity.addAll(genColumnInfoEntities);
                }
            }
            if (!CollectionUtils.isEmpty(saveTableEntity)) {
                genTableInfoDao.saveAll(saveTableEntity);
            }
            if (!CollectionUtils.isEmpty(saveColumnEntity)) {
                genColumnInfoDao.saveAll(saveColumnEntity);
            }
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
     * 同步表信息
     *
     * @param tableId 表id
     * @return 操作结果 true表示成功，false表示失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncTable(String tableId) {
        GenTableInfoEntity dbTableInfoEntity = genTableInfoDao.findById(tableId);
        ThrowUtils.notNull(dbTableInfoEntity, "数据不存在");
        String tableName = dbTableInfoEntity.getTableName();
        GenDataSourceEntity dataSourceEntity = genDataSourceDao.findById(dbTableInfoEntity.getDataSourceId());
        ThrowUtils.notNull(dataSourceEntity, "数据源不存在");
        JDBCUtils jdbcUtils = null;
        try {
            JDBCConfig jdbcConfig = JDBCConfig.Builder.of(dataSourceEntity.getUrl(), dataSourceEntity.getName(), dataSourceEntity.getName(), dataSourceEntity.getDbType().getDriverClassName()).build();
            jdbcUtils = JDBCUtils.create(jdbcConfig);
            DataBaseTypeEnums dbType = dataSourceEntity.getDbType();
            IDataBaseQuery dataBaseQuery = queryMap.get(dbType);
            ThrowUtils.notNull(dataBaseQuery, String.format("暂不支持该数据类型：%s", dbType));
            JdbcTemplate jdbcTemplate = jdbcUtils.getJdbcTemplate();
            GenTableInfoEntity tableInfoEntity = dataBaseQuery.selectTableByTableName(jdbcTemplate, tableName);
            ThrowUtils.notNull(tableInfoEntity, String.format("表`%s`不存在", tableName));
            GenInfoHelper.parseTableInfo(dataSourceEntity, tableInfoEntity, Objects.requireNonNullElse(dbTableInfoEntity.getExtConfig(), new TableExtConfig()).getModuleName());
            tableInfoEntity.setId(tableId);
            List<GenColumnInfoEntity> genColumnInfoEntities = dataBaseQuery.selectTableColumnsByTableName(jdbcTemplate, tableName);
            GenInfoHelper.parseColumnInfos(tableInfoEntity, genColumnInfoEntities);
            genColumnInfoDao.deleteByTableId(tableId);
            genTableInfoDao.updateById(tableInfoEntity);
            genColumnInfoDao.saveAll(genColumnInfoEntities);
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
     * @param id 表信息ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeById(String id) {
        genTableInfoDao.deleteById(id);
        genColumnInfoDao.deleteByTableId(id);
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
    public Boolean updateById(GenTableInfoFormRequest formRequest) {
        List<GenColumnInfoFormRequest> columns = formRequest.getColumns();
        ThrowUtils.notEmpty(columns, "字段信息不能为空");
        Boolean menuExists = genTableInfoDao.exists(GenTableInfoEntity::getId, formRequest.getId());
        ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "表信息不存在");
        for (GenColumnInfoFormRequest column : columns) {
            genColumnInfoDao.updateFormRequest(column);
        }
        return genTableInfoDao.updateFormRequest(formRequest);
    }

    /**
     * 根据ID查询表信息
     *
     * @param id 表信息ID
     * @return 表信息字段信息
     */
    @Override
    public GenTableColumnVo getById(Long id) {
        GenTableInfoEntity tableInfoEntity = genTableInfoDao.findById(id);
        GenTableColumnVo vo = genTableInfoConverter.toVo(tableInfoEntity);
        if (Objects.nonNull(vo)) {
            List<GenColumnInfoEntity> list = genColumnInfoDao.findList(GenColumnInfoEntity::getTableId, id);
            vo.setColumn(genColumnInfoConverter.toResponse(list));
        }
        return vo;
    }


    /**
     * 分页查询已存在的表信息
     *
     * @param queryRequest 查询条件封装对象，包含分页参数和查询条件
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Override
    public PageResponse<GenTableInfoResponse> selectExistsPage(GenTableInfoQueryRequest queryRequest) {
        Page<GenTableInfoEntity> page = genTableInfoDao.queryPageRequest(PageTool.getPage(queryRequest), queryRequest);
        return genTableInfoConverter.toResponse(page);
    }

    /**
     * 分页查询不存在的表信息
     *
     * @param queryRequest 数据库查询条件封装对象，包含分页参数和数据库连接信息
     * @return 分页结果封装对象，包含表信息列表和分页信息
     */
    @Override
    public List<GenTableInfoResponse> selectNoExistsList(DataBaseQueryRequest queryRequest) {
        String tableName = queryRequest.getTableName();
        Long dataSourceId = queryRequest.getDataSourceId();
        GenDataSourceEntity dataSourceEntity = genDataSourceDao.findById(dataSourceId);
        ThrowUtils.notNull(dataSourceEntity, "数据源不存在");
        JDBCUtils jdbcUtils = null;
        try {
            JDBCConfig jdbcConfig = JDBCConfig.Builder.of(dataSourceEntity.getUrl(), dataSourceEntity.getName(), dataSourceEntity.getName(), dataSourceEntity.getDbType().getDriverClassName()).build();
            jdbcUtils = JDBCUtils.create(jdbcConfig);
            DataBaseTypeEnums dbType = dataSourceEntity.getDbType();
            IDataBaseQuery dataBaseQuery = queryMap.get(dbType);
            ThrowUtils.notNull(dataBaseQuery, String.format("暂不支持该数据类型：%s", dbType));
            List<GenTableInfoEntity> genTableInfoEntities = dataBaseQuery.selectListTableByLike(jdbcUtils.getJdbcTemplate(), tableName);
            if (CollectionUtils.isEmpty(genTableInfoEntities)) {
                return Collections.emptyList();
            }
            List<String> tableNames = genTableInfoDao.findTableNameByDbId(dataSourceId);
            if (CollectionUtils.isEmpty(tableNames)) {
                return genTableInfoConverter.toResponse(genTableInfoEntities);
            }
            return genTableInfoConverter.toResponse(genTableInfoEntities.stream().filter(item -> !tableNames.contains(item.getTableName())).toList());
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
