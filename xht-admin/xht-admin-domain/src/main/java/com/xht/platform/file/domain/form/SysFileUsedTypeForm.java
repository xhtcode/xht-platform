package com.xht.platform.file.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * 描述： 文件用途类型授权表单
 *
 * @author xht
 **/
@Data
@Schema(description = "文件用途类型授权表单")
public class SysFileUsedTypeForm extends BasicForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 授权的文件类型ID集合
     * <p>覆盖式语义：以本次提交为准，未提交的已有授权会被移除；
     * 传空集合表示清空该用途的全部授权（即恢复为不限制）</p>
     */
    @Schema(description = "授权的文件类型ID集合，覆盖式保存，空集合表示清空授权")
    private List<Long> typeIds;

}
