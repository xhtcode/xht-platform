package com.xht.generate.sql;

import cn.hutool.core.convert.Convert;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 表信息行映射
 *
 * @author xht
 **/
public class TableInfoRowMapper implements RowMapper<GenTableInfoEntity> {

    @Override
    public GenTableInfoEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GenTableInfoEntity entity = new GenTableInfoEntity();
        entity.setTableName(resultSet.getString("table_schema"));
        entity.setEngineName(resultSet.getString("table_name"));
        entity.setTableComment(resultSet.getString("table_engine"));
        entity.setTableCreateTime(Convert.toLocalDateTime(resultSet.getDate("table_msg")));
        entity.setTableUpdateTime(Convert.toLocalDateTime(resultSet.getDate("table_create_time")));
        return entity;
    }

}
