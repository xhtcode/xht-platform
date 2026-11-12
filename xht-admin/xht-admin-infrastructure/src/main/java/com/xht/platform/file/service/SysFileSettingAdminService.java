package com.xht.platform.file.service;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileSettingDao;
import com.xht.platform.file.domain.form.SysFileSettingUpdateForm;
import com.xht.platform.file.domain.response.SysFileSettingResponse;
import com.xht.platform.file.entity.SysFileSettingEntity;
import com.xht.platform.file.enums.ConfigOverrideDirectionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 描述： 文件全局配置管理服务
 *
 * @author xht
 **/
@Service
@RequiredArgsConstructor
public class SysFileSettingAdminService implements ISysFileSettingAdminService {

    private final SysFileSettingDao sysFileSettingDao;

    private final SysFileSettingService sysFileSettingService;

    /**
     * 配置列表
     *
     * @param group 分组，可为空
     * @return 配置列表
     */
    public List<SysFileSettingResponse> list(String group) {
        return sysFileSettingDao.findAll().stream()
                .filter(item -> !StringUtils.hasText(group) || Objects.equals(group, item.getSettingGroup()))
                .map(this::toResponse)
                .toList();
    }

    /**
     * 批量更新
     *
     * @param form 更新请求
     * @return 更新数量
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(SysFileSettingUpdateForm form) {
        if (form == null || form.getItems() == null || form.getItems().isEmpty()) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "没有需要更新的配置");
        }
        int updated = 0;
        for (SysFileSettingUpdateForm.Item item : form.getItems()) {
            if (item == null || !StringUtils.hasText(item.getKey())) {
                continue;
            }
            SysFileSettingEntity entity = sysFileSettingDao.findByKey(item.getKey())
                    .orElseThrow(() -> new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置项不存在：" + item.getKey()));
            validate(entity, item.getValue());
            int rows = sysFileSettingDao.updateValue(item.getKey(), item.getValue(), item.getVersion());
            if (rows == 0) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置已被他人修改，请刷新后重试：" + item.getKey());
            }
            updated++;
        }
        sysFileSettingService.refresh();
        return updated;
    }

    /**
     * 恢复默认值
     *
     * @param keys 配置键
     * @return 恢复数量
     */
    @Transactional(rollbackFor = Exception.class)
    public int reset(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "没有需要恢复的配置");
        }
        int reset = 0;
        for (String key : keys) {
            SysFileSettingEntity entity = sysFileSettingDao.findByKey(key)
                    .orElseThrow(() -> new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置项不存在：" + key));
            sysFileSettingDao.updateValue(key, entity.getDefaultValue(), entity.getVersion());
            reset++;
        }
        sysFileSettingService.refresh();
        return reset;
    }

    /**
     * 校验配置值
     */
    private void validate(SysFileSettingEntity entity, String value) {
        if (value == null) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置值不能为空：" + entity.getSettingKey());
        }
        String type = entity.getValueType();
        if ("bool".equalsIgnoreCase(type)) {
            if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置值必须为 true/false：" + entity.getSettingKey());
            }
            return;
        }
        if ("int".equalsIgnoreCase(type) || "decimal".equalsIgnoreCase(type)) {
            BigDecimal decimal;
            try {
                decimal = new BigDecimal(value.trim());
            } catch (NumberFormatException e) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置值必须为数字：" + entity.getSettingKey());
            }
            if (StringUtils.hasText(entity.getMinValue())
                    && decimal.compareTo(new BigDecimal(entity.getMinValue())) < 0) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置值小于最小值：" + entity.getSettingKey());
            }
            if (StringUtils.hasText(entity.getMaxValue())
                    && decimal.compareTo(new BigDecimal(entity.getMaxValue())) > 0) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置值大于最大值：" + entity.getSettingKey());
            }
        }
        if (StringUtils.hasText(entity.getOverrideOptions())) {
            boolean matched = Stream.of(entity.getOverrideOptions().split(","))
                    .map(String::trim)
                    .anyMatch(option -> option.equalsIgnoreCase(value));
            if (!matched) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "配置值不在可选范围：" + entity.getSettingKey());
            }
        }
    }

    /**
     * 转响应
     */
    private SysFileSettingResponse toResponse(SysFileSettingEntity entity) {
        SysFileSettingResponse response = new SysFileSettingResponse();
        response.setSettingKey(entity.getSettingKey());
        response.setSettingValue(entity.getSettingValue());
        response.setValueType(entity.getValueType());
        response.setSettingGroup(entity.getSettingGroup());
        response.setSettingName(entity.getSettingName());
        response.setDescription(entity.getDescription());
        response.setDefaultValue(entity.getDefaultValue());
        response.setMinValue(entity.getMinValue());
        response.setMaxValue(entity.getMaxValue());
        response.setUserOverridable(entity.getUserOverridable());
        response.setOverrideDirection(ConfigOverrideDirectionEnum.getByValue(entity.getOverrideDirection()));
        response.setOverrideMin(entity.getOverrideMin());
        response.setOverrideMax(entity.getOverrideMax());
        response.setOverrideOptions(entity.getOverrideOptions());
        response.setOverrideHint(entity.getOverrideHint());
        response.setVersion(entity.getVersion());
        return response;
    }

}
