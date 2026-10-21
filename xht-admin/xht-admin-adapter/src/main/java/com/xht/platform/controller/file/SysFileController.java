package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.service.ISysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 描述： 系统文件接口
 * <p>
 * 提供系统文件记录元数据的删除（/remove/{fileId}）、详情查询（/get/{fileId}）与分页查询（/page）；
 * 围绕文件内容的操作类业务端点（上传、下载、预览等）见 {@link FileStorageController}；
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
     * 根据文件ID删除文件记录（逻辑删除），同步清理物理文件与缩略图
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

}
