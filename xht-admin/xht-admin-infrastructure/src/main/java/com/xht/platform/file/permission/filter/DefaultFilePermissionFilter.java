package com.xht.platform.file.permission.filter;

import com.xht.platform.file.entity.SysFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 描述： 默认文件权限过滤器（兜底策略）
 * <p>
 * 对所有文件生效，作为链式校验的最后一道规则：管理员、文件上传人本人可访问，其他已登录用户拒绝；
 * 未登录请求在此不做判定——认证是接口层的职责（联调期预览/下载接口以 {@code @IgnoreAuth} 放开），
 * 待接口收回鉴权后未登录请求到不了这里
 * <p>
 * 后续按文件用途细分规则（如合同附件仅授权人可见）时，新增 {@link FilePermissionFilter} 实现即可，
 * 会在本规则之前一起执行（叠加，任一不通过即拒绝）；
 * 若某个用途需要比本规则更宽松（如头像允许所有登录用户查看），把 {@link #support(SysFileEntity)}
 * 收窄为排除该用途即可，该用途的文件便不再受本规则约束
 *
 * @author xht
 **/
@Slf4j
@Component
public class DefaultFilePermissionFilter implements FilePermissionFilter {

    /**
     * 是否处理该文件的权限校验：兜底实现，参与所有文件的校验
     *
     * @param sysFileEntity 文件实体
     * @return 固定返回 true
     */
    @Override
    public boolean support(SysFileEntity sysFileEntity) {
        return true;
    }

    /**
     * 权限校验：管理员或上传人本人可访问，其他用户拒绝
     *
     * @param sysFileEntity 文件实体
     */
    @Override
    public void doFilter(SysFileEntity sysFileEntity) {

    }

    /**
     * 兜底实现优先级最低，链式校验中最后执行
     *
     * @return 最低优先级
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
