package com.xht.platform.file.permission;

import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.permission.filter.DefaultFilePermissionFilter;
import com.xht.platform.file.permission.filter.FilePermissionFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述： 文件权限校验上下文
 * <p>
 * 汇聚容器中全部 {@link FilePermissionFilter} 实现（Spring 注入 {@code List} 时已按 {@code Ordered} 升序排列），
 * 为文件内容类接口提供统一入口：按 order 从小到大依次执行所有 {@link FilePermissionFilter#support(SysFileEntity)}
 * 命中的实现（链式，不短路成功），任意一个抛异常即整体拒绝；
 * {@link DefaultFilePermissionFilter} 优先级最低且始终命中，作为兜底规则最后执行
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class XhtFilePermissionContext {

    /**
     * 文件权限过滤器集合（按 order 升序）
     */
    private final List<FilePermissionFilter> filePermissionFilters;

    /**
     * 执行文件权限校验：所有命中的过滤器依次执行，校验不通过抛业务异常（FILE_ACCESS_DENIED）
     *
     * @param sysFileEntity 文件实体
     */
    public void check(SysFileEntity sysFileEntity) {
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        for (FilePermissionFilter filePermissionFilter : filePermissionFilters) {
            if (!filePermissionFilter.support(sysFileEntity)) {
                continue;
            }
            log.debug("执行文件权限校验，过滤器：{}，文件Key：{}", filePermissionFilter.getClass().getSimpleName(), sysFileEntity.getFileStorageKey());
            filePermissionFilter.doFilter(sysFileEntity);
        }
    }

}
