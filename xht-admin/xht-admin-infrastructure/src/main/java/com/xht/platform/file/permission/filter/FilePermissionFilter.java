package com.xht.platform.file.permission.filter;

import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.permission.XhtFilePermissionContext;
import org.springframework.core.Ordered;

/**
 * 描述： 文件权限过滤器
 * <p>
 * 文件内容类接口（预览初始化、预览、下载）在输出内容前统一经过本接口校验；
 * 每个实现只负责一类访问规则，通过 {@link #support(SysFileEntity)} 声明适用范围，
 * 由 {@link XhtFilePermissionContext} 按 {@link #getOrder()} 从小到大依次执行**所有命中**的实现（链式校验），
 * 任意一个校验不通过即整体拒绝，抛 {@link com.xht.framework.exception.BusinessException}（错误码 FILE_ACCESS_DENIED）
 *
 * @author xht
 **/
public interface FilePermissionFilter extends Ordered {

    /**
     * 是否处理该文件的权限校验
     * <p>按文件用途（{@link SysFileEntity#getFileUsed()}）、存储类型、文件状态等维度判断，
     * 返回 true 表示本实现参与该文件的链式校验；兜底实现固定返回 true，即每个文件最后都会过一遍兜底规则</p>
     *
     * @param sysFileEntity 文件实体
     * @return true：参与该文件的校验
     */
    boolean support(SysFileEntity sysFileEntity);

    /**
     * 执行权限校验，校验不通过抛业务异常
     *
     * @param sysFileEntity 文件实体
     */
    void doFilter(SysFileEntity sysFileEntity);

}
