package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述： 文件预览类型配置实体
 * <p>
 * 对应表 sys_file_type，记录「预览类型编码/名称」与「文件后缀 → 预览类型」的映射
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_type")
public class SysFileTypeEntity extends BasicEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 预览类型编码(image/pdf/video/audio/text/json/other)
     */
    @TableField(value = "preview_type")
    private PreviewTypeEnum previewType;

    /**
     * 预览类型名称(图片/PDF/视频/音频/文本/JSON/其他)
     */
    @TableField(value = "preview_name")
    private String previewName;

    /**
     * 文件后缀（单个，不带点，如：jpg）
     */
    @TableField(value = "file_suffix")
    private String fileSuffix;

    /**
     * 预览来源：original/convert
     */
    @TableField(value = "preview_source")
    private PreviewSourceEnum previewSource;

    /**
     * 转换目标格式，如 pdf
     */
    @TableField(value = "convert_target")
    private String convertTarget;

    /**
     * 是否启用：1 是
     */
    @TableField(value = "enabled")
    private Integer enabled;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

}
