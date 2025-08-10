package com.xht.generate.sql;

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
        entity.setId(0L);
        entity.setTableId(0L);
        entity.setColumnName("");
        entity.setDbDataType("");
        entity.setColumnComment("");
        entity.setExtConfig(null);
        entity.setDefaultValue("");
        entity.setIsRequired(0);
        entity.setIsPrimary(0);
        entity.setIsForeign(0);
        entity.setForeignTable("");
        entity.setForeignColumn("");
        entity.setSortOrder(0);
        return entity;
    }
}
