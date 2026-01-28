package com.xht.modules.strategy;

import com.xht.modules.common.enums.DataBaseTypeEnums;
import com.xht.modules.generate.domain.bo.ColumnBo;
import com.xht.modules.generate.domain.bo.TableBo;
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
     * @return {@link TableBo} 表信息对象
     */
    public abstract TableBo selectTableByTableName(final JdbcTemplate jdbcTemplate, String tableName);

    /**
     * 根据表名模糊查询多表信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return {@link TableBo} 表信息对象
     */
    public abstract List<TableBo> selectListTableByLike(final JdbcTemplate jdbcTemplate, String tableName);


    /**
     * 根据表名查询字段信息
     *
     * @param jdbcTemplate JDBC模板，用于执行SQL查询
     * @param tableName    表名
     * @return {@link ColumnBo}字段信息列表
     */
    public abstract List<ColumnBo> selectTableColumnsByTableName(final JdbcTemplate jdbcTemplate, String tableName);

    /**
     * 支持的解析类型
     *
     * @return 解析类型枚举 {@link DataBaseTypeEnums}
     */
    public abstract DataBaseTypeEnums support();

}
