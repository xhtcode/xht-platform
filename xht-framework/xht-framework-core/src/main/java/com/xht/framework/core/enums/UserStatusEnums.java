package com.xht.framework.core.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举类
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum UserStatusEnums implements IEnum<Integer> {
    /**
     * 正常状态
     * <ul>
     *     <li>核心语义：账号完全可用</li>
     *     <li>触发场景示例：用户完成注册 + 激活，无任何限制</li>
     *     <li>账号可用性：完全可用</li>
     *     <li>对应业务动作：允许登录、使用所有功能</li>
     * </ul>
     */
    NORMAL(0, "正常"),

    /**
     * 未激活状态
     * <ul>
     *     <li>核心语义：账号已创建但未完成验证</li>
     *     <li>触发场景示例：用户注册后未验证手机号 / 邮箱，或新账号未启用</li>
     *     <li>账号可用性：不可用（未生效）</li>
     *     <li>对应业务动作：提示用户完成验证，验证后转为 “正常”</li>
     * </ul>
     */
    UNACTIVATED(1, "未激活"),

    /**
     * 禁用状态
     * <ul>
     *     <li>核心语义：管理员手动限制账号使用</li>
     *     <li>触发场景示例：违规操作、账号异常（需人工干预）</li>
     *     <li>账号可用性：不可用（可恢复）</li>
     *     <li>对应业务动作：管理员手动解除禁用后转为 “正常”</li>
     * </ul>
     */
    DISABLED(2, "禁用"),

    /**
     * 锁定状态
     * <ul>
     *     <li>核心语义：安全策略触发的临时限制</li>
     *     <li>触发场景示例：密码连续输错 3 次、异地登录触发安全校验</li>
     *     <li>账号可用性：不可用（临时）</li>
     *     <li>对应业务动作：超时自动解锁，或用户验证后解锁</li>
     * </ul>
     */
    LOCKED(3, "锁定"),

    /**
     * 过期状态
     * <ul>
     *     <li>核心语义：账号有效期已结束</li>
     *     <li>触发场景示例：临时账号到期、付费会员 / 权限到期</li>
     *     <li>账号可用性：不可用（需续期）</li>
     *     <li>对应业务动作：用户续费 / 延长有效期后转为 “正常”</li>
     * </ul>
     */
    EXPIRED(4, "过期");

    /**
     * 状态值
     */
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

}
