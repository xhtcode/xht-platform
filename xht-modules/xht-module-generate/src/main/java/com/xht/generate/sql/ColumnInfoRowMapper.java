package com.xht.generate.sql;

import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段信息映射
 *
 * @author xht
 **/
public class ColumnInfoRowMapper implements RowMapper<GenColumnInfoEntity> {

    @Override
    public GenColumnInfoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenColumnInfoEntity entity = new GenColumnInfoEntity();
        entity.setColumnName(rs.getString("column_name"));
        entity.setDbDataType(rs.getString("column_db_type"));
        entity.setColumnComment(rs.getString("column_comment"));
        entity.setColumnLength(rs.getInt("column_length"));
        int isRequired = rs.getInt("is_required");
        entity.setIsRequired(GenStatusEnums.getByValue(isRequired));
        int isPk = rs.getInt("is_pk");
        entity.setIsPrimary(GenStatusEnums.getByValue(isPk));
        entity.setSortOrder(rs.getInt("column_sort"));
        return entity;
    }

}
