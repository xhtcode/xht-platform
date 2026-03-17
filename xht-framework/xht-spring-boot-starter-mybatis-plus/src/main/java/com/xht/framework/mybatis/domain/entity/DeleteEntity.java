package com.xht.framework.mybatis.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 ：
 *
 * @author : xht
 **/
@Data
public class DeleteEntity extends Entity implements Serializable {

    /**
     * 是否删除(0未删除1已经删除)
     */
    @TableLogic
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private DelFlagEnum delFlag;

}
