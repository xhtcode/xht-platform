package com.xht.platform.file.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.file.enums.FileStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 描述： 系统文件表单（新增/修改共用）
 * <p>
 * 校验分组说明：所有约束均显式指定分组，新增操作（{@link Groups.Create}）要求文件原始名称必填，
 * 修改操作（{@link Groups.Update}）仅校验已传字段的长度与数值范围；
 * 修改时服务端仅更新表单中非空的字段，未传字段保持原值不变
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统文件表单")
public class SysFileForm extends BasicForm {

}
