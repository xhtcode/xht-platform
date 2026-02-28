package com.xht.modules.admin;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.core.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.security.annotation.IgnoreAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xht
 **/
@RestController
public class TestController {

    @IgnoreAuth(aop = false)
    @GetMapping("/")
    @BLog(value = "测试", description = "测试")
    public R<String> s() {
        return R.ok().build(IdUtil.fastSimpleUUID());
    }
}
