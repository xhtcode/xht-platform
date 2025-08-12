package com.xht.generate.strategy;

import com.xht.generate.constant.DataBaseTypeEnums;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 解析导入策略
 *
 * @author xht
 **/
public abstract class IDataBaseQuery {

    /**
     * 根据表名查询单表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return {@link GenTableInfoEntity} 表信息实体对象
     */
    public abstract GenTableInfoEntity selectTableByTableName(final JdbcTemplate jdbcTemplate, String tableName);

    /**
     * 根据表名模糊查询多表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName      表名
     * @return {@link GenTableInfoEntity} 表信息实体列表
     */
    public abstract List<GenTableInfoEntity> selectPageTableByLike(final JdbcTemplate jdbcTemplate, String tableName);


    /**
     * 根据表名查询字段信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return 字段信息列表
     */
    public abstract List<GenColumnInfoEntity> selectTableColumnsByTableName(final JdbcTemplate jdbcTemplate, String tableName);

    /**
     * 支持的解析类型
     *
     * @return 解析类型枚举 {@link DataBaseTypeEnums}
     */
    protected abstract DataBaseTypeEnums support();

}
