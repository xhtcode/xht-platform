package com.xht.platform.strategy;

import com.xht.platform.common.enums.DataBaseTypeEnum;
import com.xht.platform.generate.bo.ColumnBO;
import com.xht.platform.generate.bo.TableBO;
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
     * @return {@link TableBO} 表信息对象
     */
    public abstract TableBO selectTableByTableName(final JdbcTemplate jdbcTemplate, String tableName);

    /**
     * 根据表名模糊查询多表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return {@link TableBO} 表信息对象
     */
    public abstract List<TableBO> selectListTableByLike(final JdbcTemplate jdbcTemplate, String tableName);


    /**
     * 根据表名查询字段信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return {@link ColumnBO}字段信息列表
     */
    public abstract List<ColumnBO> selectTableColumnsByTableName(final JdbcTemplate jdbcTemplate, String tableName);

    /**
     * 支持的解析类型
     *
     * @return 解析类型枚举 {@link DataBaseTypeEnum}
     */
    public abstract DataBaseTypeEnum support();

}
