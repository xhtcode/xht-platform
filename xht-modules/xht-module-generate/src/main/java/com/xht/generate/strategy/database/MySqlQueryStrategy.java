package com.xht.generate.strategy.database;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.generate.constant.DataBaseTypeEnums;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.DataBaseQueryRequest;
import com.xht.generate.sql.ColumnInfoRowMapper;
import com.xht.generate.sql.TableInfoRowMapper;
import com.xht.generate.strategy.IDataBaseQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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
     * 根据表名查询单表信息
     */
    private static final String QUERY_EQ_TABLE_NAME = QUERY_TABLE_SQL + " AND table_name = ?";

    /**
     * 查询表字段信息
     */
    private static final String QUERY_COLUMN_SQL = """
            SELECT
            	table_name,
            	table_schema,
            	column_name,
                ( CASE WHEN is_nullable = 'no' THEN '1' ELSE '0' END ) AS is_required,
            	( CASE WHEN column_key = 'PRI' THEN '1' ELSE '0' END ) AS is_pk,
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
        return jdbcTemplate.queryForObject(QUERY_EQ_TABLE_NAME, new TableInfoRowMapper(), tableName);
    }

    /**
     * 根据表名模糊查询多表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param request      查询请求
     * @return {@link GenTableInfoEntity} 表信息实体列表
     */
    @Override
    public PageResponse<GenTableInfoEntity> selectPageTableByLike(JdbcTemplate jdbcTemplate, DataBaseQueryRequest request) {
        StringBuilder sql = new StringBuilder(QUERY_TABLE_SQL);
        String tableName = request.getTableName();
        Page<Object> page = PageTool.getPage(request);
        // 添加表名模糊查询条件
        if (StringUtils.hasText(tableName)) {
            sql.append(" AND table_name LIKE CONCAT('%', ?, '%') ");
        }
        // 先排序再分页（修正SQL语法错误）
        sql.append(" ORDER BY create_time DESC ");
        sql.append(" LIMIT ?, ? ");
        // 构建COUNT查询语句（避免嵌套查询，提高性能）
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM (").append(QUERY_TABLE_SQL).append(") t");
        if (StringUtils.hasText(tableName)) {
            countSqlBuilder.append(" WHERE table_name LIKE CONCAT('%', ?, '%') ");
        }
        String countSql = countSqlBuilder.toString();
        // 执行COUNT查询（注意参数顺序）
        Object[] countParams = StringUtils.hasText(tableName) ? new Object[]{tableName} : new Object[]{};
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, countParams);
        total = Objects.requireNonNullElse(total, 0L);

        // 修正分页边界判断逻辑
        if (total == 0 || page.getCurrent() >= (total + page.getSize() - 1) / page.getSize()) {
            return PageTool.cloneEmpty(page);
        }

        // 计算总页数
        long totalPages = (total - 1) / page.getSize() + 1;
        // 执行主查询（注意参数顺序）
        Object[] queryParams = StringUtils.hasText(tableName) ? new Object[]{tableName, page.offset(), page.getSize()} : new Object[]{page.offset(), page.getSize()};
        List<GenTableInfoEntity> records = jdbcTemplate.query(sql.toString(), new TableInfoRowMapper(), queryParams);
        // 构建返回结果
        PageResponse<GenTableInfoEntity> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(page.getCurrent());
        pageResponse.setSize(page.getSize());
        pageResponse.setPages(totalPages);
        pageResponse.setTotal(total);
        pageResponse.setRecords(records);
        return pageResponse;

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
