package com.xht.modules.admin.router.dto;

import com.xht.framework.core.domain.vo.IVO;

import java.util.HashMap;

import static com.xht.modules.admin.router.constant.RouterConstant.*;

/**
 * 描述 ： 路由信息
 *
 * @author xht
 * @version : 1.0
 **/
@SuppressWarnings("unused")
public class RouterDTO extends HashMap<String, Object> implements IVO {


    /**
     * 获取名称
     *
     * @return 返回存储的名称字符串
     */
    public String getName() {
        return get(NAME_KEY);
    }

    /**
     * 设置名称
     *
     * @param name 要设置的名称字符串
     */
    public void setName(String name) {
        put(NAME_KEY, name);
    }

    /**
     * 获取路径
     *
     * @return 返回存储的路径字符串
     */
    public String getPath() {
        return get(PATH_KEY);
    }

    /**
     * 设置路径
     *
     * @param path 要设置的路径字符串
     */
    public void setPath(String path) {
        put(PATH_KEY, path);
    }

    /**
     * 获取重定向地址
     *
     * @return 返回存储的重定向字符串
     */
    public String getRedirect() {
        return get(REDIRECT_KEY);
    }

    /**
     * 设置重定向地址
     *
     * @param redirect 要设置的重定向字符串
     */
    public void setRedirect(String redirect) {
        put(REDIRECT_KEY, redirect);
    }

    /**
     * 获取组件
     *
     * @return 返回存储的组件字符串
     */
    public String getComponent() {
        return get(COMPONENT_KEY);
    }

    /**
     * 设置组件
     *
     * @param component 要设置的组件字符串
     */
    public void setComponent(String component) {
        put(COMPONENT_KEY, component);
    }

    /**
     * 获取路由元数据
     *
     * @return 返回存储的RouterMeta对象
     */
    public RouterMetaDTO getMeta() {
        return get(META_KEY);
    }

    /**
     * 设置路由元数据
     *
     * @param meta 要设置的RouterMeta对象
     */
    public void setMeta(RouterMetaDTO meta) {
        put(META_KEY, meta);
    }

    /**
     * 根据键获取对应的值
     *
     * @param key 键名
     * @param <T> 泛型类型
     * @return 返回对应键的值，如果不存在则返回null
     */
    @SuppressWarnings("unchecked")
    private <T> T get(String key) {
        return (T) getOrDefault(key, null);
    }

}
