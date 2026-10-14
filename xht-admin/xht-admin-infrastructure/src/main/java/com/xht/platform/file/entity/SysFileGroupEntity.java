package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述： 文件分组实体
 *
 * @author xht
 **/
@Data
@TableName(value = "sys_file_group")
public class SysFileGroupEntity extends BasicEntity implements Serializable {

    /**
     * 文件分组ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件分组名称
     */
    private String fileGroupName;

    /**
     * 文件分组描述
     */
    private String fileGroupDesc;

}
