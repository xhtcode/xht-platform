package com.xht.platform.file.permission.filter;

import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileActionEnum;
import com.xht.platform.file.permission.FileAccessEvaluator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 描述： 默认文件权限过滤器（兜底策略）
 * <p>
 * 对所有文件生效，作为链式校验的最后一道规则：统一委托 {@link FileAccessEvaluator}，
 * 由归属/管理员、公开范围、分享权限、业务提供者和配置闸门共同决定；
 * 未登录访问私有文件会被拒绝，公开文件按匿名策略放行
 * <p>
 * 后续按文件用途细分规则时，新增 {@link com.xht.platform.file.permission.FilePermissionProvider} 实现即可，
 * 决策器会把所有命中的提供者决策取交集
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultFilePermissionFilter implements FilePermissionFilter {

    private final FileAccessEvaluator fileAccessEvaluator;

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
     * 权限校验：通过统一决策器判断当前主体能否查看
     *
     * @param sysFileEntity 文件实体
     */
    @Override
    public void doFilter(SysFileEntity sysFileEntity) {
        fileAccessEvaluator.check(sysFileEntity, fileAccessEvaluator.currentSubject(), FileActionEnum.PREVIEW);
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
