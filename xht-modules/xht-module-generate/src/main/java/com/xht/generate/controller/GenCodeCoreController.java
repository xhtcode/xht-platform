package com.xht.generate.controller;

import com.xht.framework.core.domain.R;
import com.xht.generate.domain.request.GenCodeCoreRequest;
import com.xht.generate.service.IGenCodeCoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R<Boolean> generateCode(@RequestBody GenCodeCoreRequest genCodeCoreRequest) {
        genCodeCoreService.generateCode(genCodeCoreRequest);
        return R.ok();
    }

}
