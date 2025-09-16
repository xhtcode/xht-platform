package com.xht.generate.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表信息 业务对象
 *
 * @author xht
 **/
@Data
public class TableBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 引擎名称
     */
    private String engineName;
    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 表创建时间
     */
    private LocalDateTime tableCreateTime;

    /**
     * 表更新时间
     */
    private LocalDateTime tableUpdateTime;

}
