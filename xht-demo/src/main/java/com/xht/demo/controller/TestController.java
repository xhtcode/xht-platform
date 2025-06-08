package com.xht.demo.controller;

import com.xht.framework.core.domain.R;
import com.xht.demo.domain.Users;
import com.xht.demo.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xht
 **/
@Tag(name = "测试模块")
@SuppressWarnings("all")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final UsersService usersService;

    @Operation(summary = "测试接口", description = "测试接口描述")
    @GetMapping
    public R<String> test() {
        return R.ok("Hello World");
    }

    @Operation(summary = "测试异常接口", description = "测试异常接口描述")
    @GetMapping("/test/error")
    public R<String> error() {
        System.out.println(1 / 0);
        return R.ok();
    }

    /**
     * 用户列表接口
     *
     * @return 用户列表
     */
    @Operation(summary = "用户列表接口", description = "用户列表接口描述")
    @GetMapping("/users")
    public R<List<Users>> users() {
        return R.ok(usersService.list());
    }

}
