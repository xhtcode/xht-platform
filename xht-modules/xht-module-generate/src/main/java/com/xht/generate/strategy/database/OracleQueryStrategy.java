package com.xht.generate.strategy.database;

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
 * oracle 解析导入
 *
 * @author xht
 **/
@Slf4j
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class OracleQueryStrategy extends IDataBaseQuery {

    /**
     * 查询表信息
     */
    private static final String QUERY_TABLE_SQL = """
            SELECT
              table_name as table_name,
              'oracle' as engine_name,
              comments as table_comment,
              created as table_create_time,
              last_ddl_time as table_update_time
            FROM
              user_tab_comments utc
            JOIN
              user_tables ut ON utc.table_name = ut.table_name
            WHERE
              utc.table_type = 'TABLE'
            """;

    /**
     * 根据表名查询单表信息
     */
    private static final String QUERY_EQ_TABLE_NAME = QUERY_TABLE_SQL + " AND utc.table_name = ?";

    /**
     * 查询表字段信息
     */
    private static final String QUERY_COLUMN_SQL = """
            SELECT
                utc.table_name,
                '' as table_schema,
                utc.column_name,
                (CASE WHEN uc.nullable = 'N' THEN '1' ELSE '0' END) AS is_required,
                (CASE WHEN uc.constraint_type = 'P' THEN '1' ELSE '0' END) AS is_pk,
                ucc.column_id AS sort,
                utc.comments as column_comment,
                ucc.data_type AS column_db_type,
                ucc.data_length AS column_length
            FROM
                user_tab_columns ucc
            JOIN
                user_col_comments utc ON ucc.table_name = utc.table_name AND ucc.column_name = utc.column_name
            LEFT JOIN (
                SELECT
                    ucc_pk.table_name,
                    ucc_pk.column_name,
                    uc.constraint_type
                FROM
                    user_cons_columns ucc_pk
                JOIN
                    user_constraints uc ON ucc_pk.constraint_name = uc.constraint_name
                WHERE
                    uc.constraint_type = 'P'
            ) uc ON ucc.table_name = uc.table_name AND ucc.column_name = uc.column_name
            WHERE
                ucc.table_name = ?
            ORDER BY
                ucc.column_id
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
        var page = PageTool.getPage(request);

        // 添加表名模糊查询条件
        if (StringUtils.hasText(tableName)) {
            sql.append(" AND utc.table_name LIKE '%' || UPPER(?) || '%' ");
        }

        // 先排序再分页
        sql.append(" ORDER BY created DESC ");

        // 构建COUNT查询语句
        StringBuilder countSqlBuilder = new StringBuilder("SELECT COUNT(*) FROM (").append(QUERY_TABLE_SQL).append(") t");
        if (StringUtils.hasText(tableName)) {
            countSqlBuilder.append(" WHERE table_name LIKE '%' || UPPER(?) || '%' ");
        }
        String countSql = countSqlBuilder.toString();

        // 执行COUNT查询
        Object[] countParams = StringUtils.hasText(tableName) ? new Object[]{tableName} : new Object[]{};
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, countParams);
        total = Objects.requireNonNullElse(total, 0L);

        // 分页边界判断
        if (total == 0 || page.getCurrent() >= (total + page.getSize() - 1) / page.getSize()) {
            return PageTool.cloneEmpty(page);
        }

        // 使用Oracle分页查询
        String paginatedSql = "SELECT * FROM (SELECT ROWNUM rn, t.* FROM (" + sql.toString() + ") t WHERE ROWNUM <= ?) WHERE rn > ?";

        // 计算总页数
        long totalPages = (total - 1) / page.getSize() + 1;

        // 执行主查询
        Object[] queryParams = StringUtils.hasText(tableName)
                ? new Object[]{tableName, page.getCurrent() * page.getSize(), (page.getCurrent() - 1) * page.getSize()}
                : new Object[]{page.getCurrent() * page.getSize(), (page.getCurrent() - 1) * page.getSize()};

        List<GenTableInfoEntity> records = jdbcTemplate.query(paginatedSql, new TableInfoRowMapper(), queryParams);

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
        return DataBaseTypeEnums.ORACLE;
    }
}