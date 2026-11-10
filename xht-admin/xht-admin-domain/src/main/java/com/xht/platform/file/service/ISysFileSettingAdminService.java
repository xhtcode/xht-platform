package com.xht.platform.file.service;

import com.xht.platform.file.domain.form.SysFileSettingUpdateForm;
import com.xht.platform.file.domain.response.SysFileSettingResponse;

import java.util.List;

/**
 * 描述： 文件全局配置管理服务接口
 *
 * @author xht
 **/
public interface ISysFileSettingAdminService {

    /**
     * 配置列表
     *
     * @param group 分组，可为空
     * @return 配置列表
     */
    List<SysFileSettingResponse> list(String group);

    /**
     * 批量更新
     *
     * @param form 更新请求
     * @return 更新数量
     */
    int update(SysFileSettingUpdateForm form);

    /**
     * 恢复默认值
     *
     * @param keys 配置键
     * @return 恢复数量
     */
    int reset(List<String> keys);

}
