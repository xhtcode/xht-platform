package com.xht.generate.controller;

import cn.hutool.core.io.IoUtil;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import com.xht.generate.service.IGenCodeCoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
     * @param genCodeCoreRequest 代码生成核心请求参数
     * @return 生成结果，true表示生成成功，false表示生成失败
     */
    @Operation(summary = "生成代码")
    @PostMapping("/generate")
    public void generateCode(@Validated @RequestBody GenCodeCoreRequest genCodeCoreRequest, HttpServletResponse response) throws IOException {
        byte[] bytes = genCodeCoreService.generateCode(genCodeCoreRequest);
        IoUtil.write(response.getOutputStream(), true, bytes);
    }

}
