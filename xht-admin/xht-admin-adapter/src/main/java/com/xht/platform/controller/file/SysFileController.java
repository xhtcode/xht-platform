package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.common.file.bo.ChunkMergeBO;
import com.xht.platform.common.file.bo.ChunkUploadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.FileConstant;
import com.xht.platform.file.domain.form.FileMergeChunksForm;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
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

import java.util.List;

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
@RequestMapping(FileConstant.BASE_PATH)
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
                .fileUploadUser(SecurityUtils.getUserName())
                .build())
        );
    }

    /**
     * 查询已上传分片序号列表（断点续传）
     * <p>前端可据此跳过已上传分片，仅续传缺失部分</p>
     *
     * @param uploadId 分片上传标识（推荐使用文件MD5或UUID）
     * @return 已上传分片序号列表（升序），无已上传分片时返回空集合
     */
    @Operation(summary = "查询分片上传进度")
    @GetMapping("/chunk/progress/{uploadId}")
    public R<List<Integer>> findUploadedChunkIndexes(@PathVariable String uploadId) {
        return R.ok().build(sysFileService.findUploadedChunkIndexes(uploadId));
    }

    /**
     * 分片上传（单分片）
     *
     * @param file       分片文件
     * @param uploadId   分片上传标识，同一文件的所有分片共用
     * @param chunkIndex 分片序号，从0开始
     * @return 操作结果
     */
    @Operation(summary = "分片上传", parameters = {
            @Parameter(name = "file", description = "分片文件", required = true, schema = @Schema(type = "string", format = "binary")),
            @Parameter(name = "uploadId", description = "分片上传标识", required = true),
            @Parameter(name = "chunkIndex", description = "分片序号，从0开始", required = true)
    })
    @PostMapping("/chunk/upload")
    public R<Void> uploadChunk(@RequestPart(name = "file") MultipartFile file,
                               @RequestParam(name = "uploadId") String uploadId,
                               @RequestParam(name = "chunkIndex") Integer chunkIndex
    ) {
        sysFileService.uploadChunk(ChunkUploadBO
                .builder(file)
                .uploadId(uploadId)
                .chunkIndex(chunkIndex)
                .fileUploadUser(SecurityUtils.getUserName())
                .build()
        );
        return R.ok().build();
    }

    /**
     * 合并文件分片
     * <p>校验分片完整性（0 ~ chunkTotal-1 全部存在）后按序号顺序合并为完整文件并生成文件记录；
     * fileHash 非空时校验合并结果与文件MD5的一致性</p>
     *
     * @param fileMergeChunksForm 分片上传标识，与分片上传时保持一致，包含文件名、分片总数、文件用途、文件描述、文件MD5
     * @return 文件信息
     */
    @Operation(summary = "合并文件分片")
    @PostMapping("/chunk/merge")
    public R<SysFileResponse> mergeChunks(@RequestBody FileMergeChunksForm fileMergeChunksForm) {
        return R.ok().build(sysFileService.mergeChunks(ChunkMergeBO
                .builder()
                .uploadId(fileMergeChunksForm.getUploadId())
                .fileName(fileMergeChunksForm.getFileName())
                .chunkTotal(fileMergeChunksForm.getChunkTotal())
                .fileUsed(fileMergeChunksForm.getFileUsed())
                .fileDesc(fileMergeChunksForm.getFileDesc())
                .fileHash(fileMergeChunksForm.getFileHash())
                .fileUploadUser(SecurityUtils.getUserName())
                .build())
        );
    }

    /**
     * 文件下载
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @Operation(summary = "文件下载")
    @GetMapping("/download/{fileStorageKey}")
    public void downloadFile(HttpServletResponse response, @PathVariable String fileStorageKey) {
        sysFileService.downloadFile(response, fileStorageKey);
    }


    /**
     * 初始化文件预览信息
     * <p>前端打开预览前调用：返回文件名称、大小、类型以及预览/下载地址；
     * 文件用途由服务端查库取得，权限校验在服务端完成，调用方只需提供文件存储Key</p>
     *
     * @param fileStorageKey 文件存储Key
     * @return 预览初始化信息
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "初始化文件预览信息", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储Key", required = true)
    })
    @PostMapping("/preview/init")
    public R<SysFilePreviewResponse> initPreviewInfo(@RequestParam(name = "fileStorageKey") String fileStorageKey) {
        return R.ok().build(sysFileService.initPreviewInfo(fileStorageKey));
    }

    /**
     * 文件预览
     * <p>支持 Range 分段请求，可用于 PDF 分页加载、音视频拖动进度</p>
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "文件预览")
    @GetMapping("/preview")
    public void filePreview(HttpServletResponse response, @RequestParam(name = "fileStorageKey") String fileStorageKey) {
        sysFileService.filePreview(response, fileStorageKey);
    }

}
