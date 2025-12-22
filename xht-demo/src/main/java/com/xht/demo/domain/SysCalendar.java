package com.xht.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
@TableName(value ="sys_calendar")
public class SysCalendar implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日期时间
     */
    @TableField(value = "date_info")
    private Date dateInfo;

    /**
     * 日期类型
     */
    @TableField(value = "date_type")
    private String dateType;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}