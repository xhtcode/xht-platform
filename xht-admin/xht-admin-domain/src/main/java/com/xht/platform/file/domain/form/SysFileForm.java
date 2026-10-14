package com.xht.platform.file.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.platform.file.FileMetadata;
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

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件原始名称
     */
    @NotBlank(groups = Groups.Create.class, message = "文件原始名称不能为空")
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 512, message = "文件原始名称长度不能超过512")
    @Schema(name = "fileOriginalName", description = "文件原始名称")
    private String fileOriginalName;

    /**
     * 文件大小（单位：字节）
     */
    @PositiveOrZero(groups = {Groups.Create.class, Groups.Update.class}, message = "文件大小不能为负数")
    @Schema(name = "fileSize", description = "文件大小（单位：字节）")
    private Long fileSize;

    /**
     * 文件大小信息
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 32, message = "文件大小信息长度不能超过32")
    @Schema(name = "fileSizeInfo", description = "文件大小信息")
    private String fileSizeInfo;

    /**
     * 文件hash
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 100, message = "文件hash长度不能超过100")
    @Schema(name = "fileHash", description = "文件hash")
    private String fileHash;

    /**
     * 文件类型
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 128, message = "文件类型长度不能超过128")
    @Schema(name = "fileType", description = "文件类型")
    private String fileType;

    /**
     * 文件后缀
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 32, message = "文件后缀长度不能超过32")
    @Schema(name = "fileSuffix", description = "文件后缀")
    private String fileSuffix;

    /**
     * 文件状态（新增时未传默认启用）
     */
    @Schema(name = "fileStatus", description = "文件状态（新增时未传默认启用）")
    private FileStatusEnum fileStatus;

    /**
     * 文件用途
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 200, message = "文件用途长度不能超过200")
    @Schema(name = "fileUsed", description = "文件用途")
    private String fileUsed;

    /**
     * 文件存储类型
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 32, message = "文件存储类型长度不能超过32")
    @Schema(name = "fileStorageType", description = "文件存储类型")
    private String fileStorageType;

    /**
     * 文件存储key
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 500, message = "文件存储key长度不能超过500")
    @Schema(name = "fileStorageKey", description = "文件存储key")
    private String fileStorageKey;

    /**
     * 文件存储名称
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 512, message = "文件存储名称长度不能超过512")
    @Schema(name = "fileStorageName", description = "文件存储名称")
    private String fileStorageName;

    /**
     * 文件存储路径
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 500, message = "文件存储路径长度不能超过500")
    @Schema(name = "fileStoragePath", description = "文件存储路径")
    private String fileStoragePath;

    /**
     * 文件预览地址
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 500, message = "文件预览地址长度不能超过500")
    @Schema(name = "filePreviewUrl", description = "文件预览地址")
    private String filePreviewUrl;

    /**
     * 文件元数据（JSON 字符串）
     */
    @Schema(name = "fileMetadata", description = "文件元数据（JSON 字符串）")
    private FileMetadata fileMetadata;

    /**
     * 文件上传时间（新增时未传默认当前时间）
     */
    @Schema(name = "fileUploadTime", description = "文件上传时间（新增时未传默认当前时间）")
    private LocalDateTime fileUploadTime;

    /**
     * 文件关联时间（文件关联业务时时间）
     */
    @Schema(name = "fileAssociateTime", description = "文件关联时间（文件关联业务时时间）")
    private LocalDateTime fileAssociateTime;

    /**
     * 文件上传用户
     */
    @Size(groups = {Groups.Create.class, Groups.Update.class}, max = 50, message = "文件上传用户长度不能超过50")
    @Schema(name = "fileUploadUser", description = "文件上传用户")
    private String fileUploadUser;

}
