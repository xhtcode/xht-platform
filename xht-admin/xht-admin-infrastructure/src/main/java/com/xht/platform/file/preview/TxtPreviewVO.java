package com.xht.platform.file.preview;

import lombok.Data;

/**
 * 描述：文本文件分页预览响应
 * <p>一页对应文件中的一个字节块（默认 64KB，约 2 万汉字），页尾尽量对齐到换行符，
 * 保证按 {@link #offset} 续读时不会出现半个字符或半行内容</p>
 *
 * @author xht
 **/
@Data
public class TxtPreviewVO {

    /**
     * 当前页文本内容
     */
    private String content;

    /**
     * 下一页游标（字节偏移，已对齐字符边界，续读时原样回传）
     */
    private long offset;

    /**
     * 当前页号，从 1 开始
     */
    private long current;

    /**
     * 每页字节数
     */
    private long size;

    /**
     * 估算总页数（按页字节数估算，页尾对齐会让实际页数与估算值略有偏差）
     */
    private long pages;

    /**
     * 文件总字节数
     */
    private long total;

    /**
     * 是否还有更多内容
     */
    private boolean hasMore;

    /**
     * 本页使用的文本编码
     */
    private String charset;

}
