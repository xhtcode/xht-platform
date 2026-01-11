package com.xht.modules.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 描述 ：系统消息
 *
 * @author xht
 **/
@Data
@TableName(value = "sys_message")
public class SysMessageEntity extends BasicEntity {

    /**
     * 部门id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

}
