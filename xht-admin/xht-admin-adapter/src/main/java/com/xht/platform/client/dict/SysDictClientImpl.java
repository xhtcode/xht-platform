package com.xht.platform.client.dict;

import com.xht.framework.common.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.platform.common.dict.api.ISysDictClient;
import com.xht.platform.common.dict.domain.DictVO;
import com.xht.platform.dict.service.ISysDictItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述：字典项查询服务
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequestMapping("/api/sys/dict")
@RequiredArgsConstructor
public class SysDictClientImpl implements ISysDictClient {

    private final ISysDictItemService sysDictItemService;

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Override
    @IgnoreAuth(aop = false)
    @GetMapping("/code/{dictCode}")
    @Operation(summary = "根据字典编码查询")
    public R<List<DictVO>> getByDictCode(@PathVariable String dictCode) {
        return R.ok().build(sysDictItemService.getByDictCode(dictCode));
    }

}
