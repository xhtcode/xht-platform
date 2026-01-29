package com.xht.modules.generate.controller;

import cn.hutool.core.io.IoUtil;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.CharacterEnums;
import com.xht.modules.generate.domain.form.GenCodeCoreForm;
import com.xht.modules.generate.domain.vo.GenCodeCoreVo;
import com.xht.modules.generate.service.IGenCodeCoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 代码生成核心控制器
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "代码生成核心", description = "代码生成核心控制器")
@RestController
@RequestMapping("/gen/code")
@RequiredArgsConstructor
public class GenCodeCoreController {

    private final IGenCodeCoreService genCodeCoreService;

    /**
     * 生成代码
     *
     * @param genCodeCoreForm 代码生成核心请求参数
     */
    @Operation(summary = "生成代码")
    @PostMapping("/generate")
    public void generateCode(@Validated @RequestBody GenCodeCoreForm genCodeCoreForm, HttpServletResponse response) throws IOException {
        byte[] bytes = genCodeCoreService.generateCode(genCodeCoreForm);
        response.reset();
        String fileName = String.format("%s.zip", URLEncoder.encode("代码下载", CharacterEnums.UTF_8.getValue()));
        response.setHeader(HttpConstants.Header.DOWNLOAD_FILE.getValue(), fileName);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(bytes.length));
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), false, bytes);
    }

    /**
     * 预览代码
     *
     * @param genCodeCoreForm 代码生成核心请求参数
     * @return 代码列表
     */
    @Operation(summary = "生成代码")
    @PostMapping("/view")
    public R<List<GenCodeCoreVo>> viewCode(@Validated @RequestBody GenCodeCoreForm genCodeCoreForm) {
        return R.ok().build(genCodeCoreService.viewCode(genCodeCoreForm));
    }

}
