package com.xht.generate.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.generate.constant.enums.GenerateStatusEnums;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.event.GenLogEvent;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 生成日志助手类，用于创建生成日志事件
 *
 * @author xht
 **/
public final class GenLogHelper {

    /**
     * 创建成功的生成日志事件
     *
     * @param groupId           分组ID
     * @param groupTemplateSize 分组模板数量
     * @param tableIds          表ID列表
     */
    public static void success(Long groupId, Long groupTemplateSize, List<String> tableIds) {
        GenLogEvent genLogEvent = createGenLogEvent(groupId, groupTemplateSize, tableIds,
                GenerateStatusEnums.SUCCESS, null);
        SpringContextUtils.publishEvent(genLogEvent);
    }

    /**
     * 创建失败的生成日志事件
     *
     * @param groupId           分组ID
     * @param groupTemplateSize 分组模板数量
     * @param tableIds          表ID列表
     * @param errorMsg          错误信息
     */
    public static void fail(Long groupId, Long groupTemplateSize,
                            List<String> tableIds, String errorMsg) {
        GenLogEvent genLogEvent = createGenLogEvent(groupId, groupTemplateSize, tableIds,
                GenerateStatusEnums.FAIL, errorMsg);
        SpringContextUtils.publishEvent(genLogEvent);
    }

    /**
     * 创建生成日志事件的通用方法
     *
     * @param groupId           分组ID
     * @param groupTemplateSize 分组模板数量
     * @param tableIds          表ID列表
     * @param status            生成状态
     * @param errorMsg          错误信息，成功时为null
     * @return 生成日志事件
     */
    private static GenLogEvent createGenLogEvent(Long groupId, Long groupTemplateSize,
                                                 List<String> tableIds, GenerateStatusEnums status, String errorMsg) {
        GenLogEntity entity = new GenLogEntity();
        entity.setGroupId(groupId);
        entity.setBatchNo(IdUtil.getSnowflakeNextIdStr());
        entity.setGenerateTime(LocalDateTime.now());
        entity.setFileCount(calculateFileCount(groupTemplateSize, tableIds.size()));
        entity.setTableIds(CollUtil.join(tableIds, StringConstant.SEPARATOR));
        entity.setStatus(status);
        entity.setErrorMsg(errorMsg);
        return new GenLogEvent(entity);
    }

    /**
     * 计算文件数量
     *
     * @param groupId           分组ID
     * @param groupTemplateSize 分组模板数量
     * @return 文件数量
     */
    private static Long calculateFileCount(Long groupId, int groupTemplateSize) {
        // 使用Math.multiplyExact避免溢出
        return Math.multiplyExact(groupId, groupTemplateSize);
    }
}