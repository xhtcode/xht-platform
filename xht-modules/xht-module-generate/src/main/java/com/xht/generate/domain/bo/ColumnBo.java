package com.xht.generate.domain.bo;

import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字段业务对象
 *
 * @author xht
 **/
@Getter
public class ColumnBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    @Setter
    private String dbName;

    /**
     * 字段类型
     */
    @Setter
    private String dbType;

    /**
     * 字段注释
     */
    @Setter
    private String dbComment;

    /**
     * 字段长度
     */
    @Setter
    private int dbLength;

    /**
     * 字段必填：0-非必填，1-必填
     */
    private GenStatusEnums dbRequired;

    /**
     * 字段主键：0-非主键，1-主键
     */
    private IdPrimaryKeyEnums dbPrimary;

    /**
     * 字段排序
     */
    @Setter
    private Integer sortOrder;

    public void setDbRequired(int dbRequired) {
        this.dbRequired = GenStatusEnums.of(dbRequired);
    }

    public void setDbPrimary(int dbPrimary) {
        this.dbPrimary = IdPrimaryKeyEnums.of(dbPrimary);
    }
}
