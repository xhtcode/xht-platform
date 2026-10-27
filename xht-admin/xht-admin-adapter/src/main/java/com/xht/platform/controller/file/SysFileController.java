package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.service.ISysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述： 系统文件接口
 * <p>
 * 提供系统文件记录元数据的删除（/remove/{fileId}）、详情查询（/get/{fileId}）与分页查询（/page）；
 * 写操作标注 {@link BLog} 记录操作日志，业务日志记录失败不影响主流程
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "文件管理")
@RestController
@RequestMapping("/sys/files")
@RequiredArgsConstructor
public class SysFileController {

    private final ISysFileService sysFileService;

    /**
     * 根据文件ID删除文件记录（逻辑删除），同步清理物理文件
     *
     * @param fileId 文件ID
     * @return 操作结果
     */
    @BLog(value = "文件管理", description = "删除文件记录")
    @IsAdmin
    @Operation(summary = "删除文件记录")
    @PostMapping("/remove/{fileId}")
    public R<Void> remove(@PathVariable Long fileId) {
        sysFileService.removeById(fileId);
        return R.ok().build();
    }

    /**
     * 根据文件ID查询文件详情
     *
     * @param fileId 文件ID
     * @return 文件详情
     */
    @IsAdmin
    @Operation(summary = "查询文件详情")
    @GetMapping("/get/{fileId}")
    public R<SysFileResponse> get(@PathVariable Long fileId) {
        return R.ok().build(sysFileService.findById(fileId));
    }

    /**
     * 分页查询文件列表
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    @IsAdmin
    @Operation(summary = "分页查询文件列表")
    @PostMapping("/page")
    public R<PageResponse<SysFileResponse>> findPageList(@RequestBody SysFileQuery query) {
        return R.ok().build(sysFileService.findPageList(query));
    }

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return 文件信息
     */
    @BLog(value = "文件管理", description = "上传文件")
    @Operation(summary = "文件上传", parameters = {
            @Parameter(name = "file", description = "上传文件", required = true, schema = @Schema(type = "string", format = "binary"))
    })
    @PostMapping("/upload")
    public R<SysFileResponse> uploadFile(@RequestPart(name = "file") MultipartFile file,
                                         @RequestParam(value = "fileUsed", required = false) String fileUsed
    ) {
        return R.ok().build(sysFileService.uploadMultipartFile(UploadBO
                .builder(file)
                .fileUsed(fileUsed)
                .fileUploadUser("admin")
                .build())
        );
    }

    /**
     * 文件下载
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @BLog(value = "文件管理", description = "下载文件")
    @Operation(summary = "文件下载")
    @GetMapping("/download/{fileStorageKey}")
    public void downloadFile(HttpServletResponse response, @PathVariable String fileStorageKey) {
        sysFileService.downloadFile(response, fileStorageKey);
    }

    /**
     * 文件预览
     * <p>支持 Range 分段请求，可用于 PDF 分页加载、音视频拖动进度</p>
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @BLog(value = "文件管理", description = "预览文件")
    @Operation(summary = "文件预览")
    @GetMapping("/preview")
    public void filePreview(HttpServletResponse response, @RequestParam(name = "fileStorageKey") String fileStorageKey) {
        sysFileService.filePreview(response, fileStorageKey);
    }
}
