package com.xht.platform.file.domain.access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件访问决策
 * <p>权限位相互独立，DOWNLOAD 分享允许“无预览、仅下载”</p>
 *
 * @author xht
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAccessDecision implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 允许预览
     */
    private boolean view;

    /**
     * 允许下载
     */
    private boolean download;

    /**
     * 允许打印
     */
    private boolean print;

    /**
     * 允许分享
     */
    private boolean share;

    /**
     * 全部允许
     *
     * @return 决策
     */
    public static FileAccessDecision allowAll() {
        return FileAccessDecision.builder()
                .view(true)
                .download(true)
                .print(true)
                .share(true)
                .build();
    }

    /**
     * 全部拒绝
     *
     * @return 决策
     */
    public static FileAccessDecision denyAll() {
        return new FileAccessDecision();
    }

    /**
     * 仅查看
     *
     * @return 决策
     */
    public static FileAccessDecision viewOnly() {
        return FileAccessDecision.builder().view(true).build();
    }

    /**
     * 与另一个决策取交集
     *
     * @param other 另一个决策
     * @return 交集
     */
    public FileAccessDecision intersect(FileAccessDecision other) {
        if (other == null) {
            return this;
        }
        return FileAccessDecision.builder()
                .view(this.view && other.view)
                .download(this.download && other.download)
                .print(this.print && other.print)
                .share(this.share && other.share)
                .build();
    }

    /**
     * 是否至少有一项能力
     *
     * @return true：有任意能力
     */
    public boolean hasAny() {
        return view || download || print || share;
    }

}
