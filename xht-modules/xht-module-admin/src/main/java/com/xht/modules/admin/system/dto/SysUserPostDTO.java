package com.xht.modules.admin.system.dto;

import com.xht.framework.core.domain.dto.BasicDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 *
 * 描述：用户岗位关联表
 * @author xht
 **/
@Data
@Accessors(chain = true)
public class SysUserPostDTO extends BasicDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 岗位id
     */
    private Long postId;


    /**
     * 判断是否相等
     * @param userId  用户id
     * @param deptId 部门id
     * @param postId 岗位id
     * @return 等true
     */
    public boolean equals(Long userId, Long deptId, Long postId) {
        return Objects.equals(this.userId, userId) &&
                Objects.equals(this.deptId, deptId) &&
                Objects.equals(this.postId, postId)
                ;
    }
}
