package com.xht.platform.file.domain.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 分享访问上下文
 *
 * @author xht
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareAccessContext implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问主体
     */
    private FileAccessSubject subject;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 分享ID
     */
    private Long shareId;

    /**
     * 接收人记录ID
     */
    private Long receiverId;

}
