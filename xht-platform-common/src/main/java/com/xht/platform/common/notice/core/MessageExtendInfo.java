package com.xht.platform.common.notice.core;

import com.xht.framework.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息扩展信息
 *
 * @author xht
 **/
public class MessageExtendInfo extends HashMap<String, Object> {

    /**
     * 跳转URL
     */
    private final static String JUMP_URL_KEY = "jumpUrl";

    /**
     * 设置跳转url
     */
    public void setJumpUrl(String jumpUrl) {
        put(JUMP_URL_KEY, jumpUrl);
    }

    /**
     * 获取跳转url
     */
    public String getJumpUrl() {
        return (String) getOrDefault(JUMP_URL_KEY, null);
    }

}
