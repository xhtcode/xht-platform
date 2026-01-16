package com.xht.modules.admin.router.constant;

/**
 * 路由常量
 *
 * @author xht
 **/
public interface RouterConstant {
    /**
     * 组件名称
     */
    String NAME_KEY = "name";
    /**
     * 路由地址
     */
    String PATH_KEY = "path";

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    String REDIRECT_KEY = "redirect";

    /**
     * 组件地址
     */
    String COMPONENT_KEY = "component";

    /**
     * 其他元素
     */
    String META_KEY = "meta";
}
