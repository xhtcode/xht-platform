package com.xht.system.modules.authority.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.modules.authority.domain.response.RouterMetaResponse;
import lombok.Data;

/**
 * 描述 ：
 *
 * @author xht
 * @version : 1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo implements IVO {

    /**
     * 组件名称
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 其他元素
     */
    private RouterMetaResponse meta;

}
