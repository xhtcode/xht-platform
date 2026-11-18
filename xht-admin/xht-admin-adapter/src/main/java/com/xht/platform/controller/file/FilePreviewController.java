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
import org.springframework.web.context.request.WebRequest;

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
     * <p>地址格式：{@code /img/user-avatar/{fileStorageKey}}；追加 {@code ~宽x高} 尺寸后缀时按目标尺寸等比缩放输出，
     * 如 {@code /img/user-avatar/{fileStorageKey}~200x200}，省略尺寸后缀输出原图</p>
     *
     * @param response       响应
     * @param fileStorageKey 头像文件名：{@code 文件存储key[~宽x高]}
     */
    @IgnoreAuth(aop = false)
    @Operation(summary = "用户头像预览", parameters = {
            @Parameter(name = "fileStorageKey", description = "头像文件名：文件存储key[~宽x高]，不带文件后缀", required = true)
    })
    @GetMapping("/user-avatar/{fileStorageKey}")
    public void previewUserAvatar(HttpServletRequest request,
            WebRequest webRequest
            , HttpServletResponse response, @PathVariable String fileStorageKey) {
        userAvatarPreviewService.preview(request, response, fileStorageKey);
    }

}
