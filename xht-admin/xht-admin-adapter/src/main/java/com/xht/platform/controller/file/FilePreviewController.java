package com.xht.platform.controller.file;

import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.file.service.IFilePreviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述： 文件预览控制器
 * <p>提供可被 {@code <img>} 等无法携带令牌的场景直接引用的公开图片地址</p>
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "文件预览")
@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
public class FilePreviewController {

    private final IFilePreviewService userAvatarPreviewService;

    /**
     * 用户头像预览
     * <p>根据文件存储key读取头像文件流，直接写入response输出给前端预览/访问</p>
     *
     * @param request        请求对象
     * @param response       响应对象，用于输出文件流
     * @param fileStorageKey 文件存储唯一标识key（文件索引）
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "用户头像预览", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储唯一标识key（文件索引）", required = true)
    })
    @GetMapping("/user-avatar/{fileStorageKey}")
    public void previewUserAvatar(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileStorageKey) {
        userAvatarPreviewService.previewUserAvatar(request, response, fileStorageKey);
    }

}
