package com.xht.modules.sql;

import com.xht.modules.generate.domain.bo.ColumnBo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段信息映射
 *
 * @author xht
 **/
public class ColumnInfoRowMapper implements RowMapper<ColumnBo> {

    @Override
    public ColumnBo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColumnBo columnBo = new ColumnBo();
        columnBo.setDbName(rs.getString("column_name"));
        columnBo.setDbType(rs.getString("column_db_type"));
        columnBo.setDbComment(rs.getString("column_comment"));
        long columnLength = rs.getLong("column_length");
        if (columnLength > 4000) {
            columnBo.setDbLength(0);
        } else {
            columnBo.setDbLength(((Long) columnLength).intValue());
        }
        columnBo.setDbRequired(rs.getInt("is_required"));
        columnBo.setDbPrimary(rs.getInt("is_pk"));
        columnBo.setSortOrder(rs.getInt("column_sort"));
        return columnBo;
    }

}
