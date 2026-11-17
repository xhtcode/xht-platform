package com.xht.platform.controller.file;

import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.file.service.IAdminFilePreviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：文件预览控制器 管理员专用
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequestMapping("/admin/file/preview")
@RequiredArgsConstructor
public class AdminFilePreviewController {


    private final IAdminFilePreviewService adminFilePreviewService;

    /**
     * 文件预览内容
     * <p>文本文件按字节块分页返回 JSON，其余文件按原内容输出；支持 Range</p>
     *
     * @param request   请求
     * @param response  响应
     * @param previewId 预览ID
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "文件预览内容", parameters = {
            @Parameter(name = "previewId", description = "预览ID", required = true),
            @Parameter(name = "offset", description = "文本预览游标"),
            @Parameter(name = "pageNo", description = "文本预览页码"),
            @Parameter(name = "size", description = "文本预览单页字节数"),
            @Parameter(name = "charset", description = "文本编码")
    })
    @GetMapping("/preview/content")
    public void filePreview(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name = "previewId") String previewId) {
        adminFilePreviewService.filePreview(request, response, previewId);
    }

    /**
     * 文件下载
     *
     * @param response  响应
     * @param previewId 预览ID
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "文件下载", parameters = {
            @Parameter(name = "previewId", description = "预览ID", required = true)
    })
    @GetMapping("/preview/download")
    public void downloadFile(HttpServletResponse response, @RequestParam(name = "previewId") String previewId) {
        adminFilePreviewService.downloadFile(response, previewId);
    }

}
