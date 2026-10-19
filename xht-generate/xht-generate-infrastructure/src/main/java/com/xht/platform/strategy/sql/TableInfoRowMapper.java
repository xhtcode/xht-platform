package com.xht.platform.strategy.sql;

import cn.hutool.core.convert.Convert;
import com.xht.platform.generate.bo.TableBO;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 表信息行映射
 *
 * @author xht
 **/
@AllArgsConstructor
public class TableInfoRowMapper implements RowMapper<TableBO> {

    @Override
    public TableBO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        TableBO tableBo = new TableBO();
        tableBo.setTableName(resultSet.getString("table_name"));
        tableBo.setEngineName(resultSet.getString("engine_name"));
        tableBo.setTableComment(resultSet.getString("table_comment"));
        tableBo.setTableCreateTime(Convert.toLocalDateTime(resultSet.getDate("table_create_time")));
        tableBo.setTableUpdateTime(Convert.toLocalDateTime(resultSet.getDate("table_update_time")));
        return tableBo;
    }

}
