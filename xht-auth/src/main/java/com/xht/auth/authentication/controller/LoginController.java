package com.xht.auth.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录控制器
 *
 * @author xht
 **/
@Slf4j
@Tag(name = "登录管理", description = "提供登录功能")
@Controller
@RequiredArgsConstructor
public class LoginController {

    /**
     * 授权码模式：认证页面
     * @param modelAndView 视图模型对象
     * @param error 表单登录失败处理回调的错误信息
     * @return 包含登录页面视图和错误信息的ModelAndView对象
     */
    @GetMapping("/login")
    @Operation(summary = "授权码模式：认证页面", description = "授权码模式：认证页面")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }
}
