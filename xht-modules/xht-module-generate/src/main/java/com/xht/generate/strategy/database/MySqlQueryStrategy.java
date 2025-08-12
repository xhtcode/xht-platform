package com.xht.generate.strategy.database;

import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.generate.constant.DataBaseTypeEnums;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.sql.ColumnInfoRowMapper;
import com.xht.generate.sql.TableInfoRowMapper;
import com.xht.generate.strategy.IDataBaseQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * mysql 数据库解析
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class MySqlQueryStrategy extends IDataBaseQuery {

    /**
     * 查询表信息
     */
    private static final String QUERY_TABLE_SQL = """
            SELECT
              table_name as table_name,
              ENGINE as engine_name,
              table_comment as table_comment,
              create_time as table_create_time,
              update_time as table_update_time
            FROM
              information_schema.TABLES
            WHERE
              table_schema = (SELECT DATABASE())
            """;

    /**
     * 查询表字段信息
     */
    private static final String QUERY_COLUMN_SQL = """
            SELECT
            	table_name,
            	table_schema,
            	column_name,
                ( IF(is_nullable = 'no', '1', '0') ) AS is_required,
            	( IF(column_key = 'PRI', '1', '0') ) AS is_pk,
            	ordinal_position AS sort,
            	column_comment,
            	data_type AS column_db_type,
            	CHARACTER_MAXIMUM_LENGTH AS column_length
            FROM
            	information_schema.COLUMNS
            WHERE
            	table_schema = (
            	SELECT DATABASE())
            	AND table_name = ?
            ORDER BY
            	ordinal_position
            """;


    /**
     * 根据表名查询单表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return {@link GenTableInfoEntity} 表信息实体对象
     */
    @Override
    public GenTableInfoEntity selectTableByTableName(JdbcTemplate jdbcTemplate, String tableName) {
        return jdbcTemplate.queryForObject(QUERY_TABLE_SQL + " AND table_name = ?", new TableInfoRowMapper(), tableName);
    }

    /**
     * 根据表名模糊查询多表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName      表名
     * @return {@link GenTableInfoEntity} 表信息实体列表
     */
    @Override
    public List<GenTableInfoEntity> selectPageTableByLike(JdbcTemplate jdbcTemplate, String tableName) {
        StringBuilder sql = new StringBuilder(QUERY_TABLE_SQL);
        // 添加表名模糊查询条件
        if (StringUtils.hasText(tableName)) {
            sql.append(" AND table_name LIKE CONCAT('%', ?, '%') ");
        }
        Object[] queryParams = StringUtils.hasText(tableName) ? new Object[]{tableName} : new Object[]{};
        return jdbcTemplate.query(sql.toString(), new TableInfoRowMapper(), queryParams);

    }

    /**
     * 根据表名查询字段信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return 字段信息列表
     */
    @Override
    public List<GenColumnInfoEntity> selectTableColumnsByTableName(JdbcTemplate jdbcTemplate, String tableName) {
        ThrowUtils.hasText(tableName, "表名不能为空");
        return jdbcTemplate.query(QUERY_COLUMN_SQL, new ColumnInfoRowMapper(), tableName);
    }

    /**
     * 支持的解析类型
     *
     * @return 解析类型枚举 {@link DataBaseTypeEnums}
     */
    @Override
    protected DataBaseTypeEnums support() {
        return DataBaseTypeEnums.MYSQL;
    }

}
