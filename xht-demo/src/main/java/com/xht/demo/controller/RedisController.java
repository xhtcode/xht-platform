package com.xht.demo.controller;

import com.xht.framework.cache.service.RedisService;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis控制器
 *
 * @author xht
 **/
@Tag(name = "Redis控制器", description = "用于操作Redis缓存的API")
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping("/set")
    @Operation(summary = "设置Redis缓存", description = "向Redis中设置一个键为'test:key'，值为'test'的缓存")
    public R<Boolean> set() {
        redisService.set(Keys.createKey("test", "key"), "test");
        return R.ok();
    }

    @GetMapping("/get")
    @Operation(summary = "获取Redis缓存", description = "从Redis中获取键为'test:key'的缓存值")
    public R<String> get() {
        return R.ok(redisService.get(Keys.createKey("test", "key")));
    }
}
