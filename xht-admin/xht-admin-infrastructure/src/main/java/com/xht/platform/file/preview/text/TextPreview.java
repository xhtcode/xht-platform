package com.xht.platform.file.preview.text;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.ServletUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.preview.FilePreview;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * 描述： 文本文件预览
 * <p>按字节块分页返回文本内容：一页默认 64KB（约 2 万汉字、400~800 行），
 * 页尾对齐到换行符，游标按"实际消费的字节数"前进，保证跨页不会截断多字节字符</p>
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class TextPreview implements FilePreview {

    /**
     * 单页默认字节数：约 2 万汉字，前端一次渲染不卡顿
     */
    private static final int DEFAULT_PAGE_SIZE = 64 * 1024;

    /**
     * 单页最小字节数
     */
    private static final int MIN_PAGE_SIZE = 4 * 1024;

    /**
     * 单页最大字节数：再大低端设备渲染会掉帧
     */
    private static final int MAX_PAGE_SIZE = 256 * 1024;

    /**
     * 页尾对齐换行符时允许的最大延伸字节数，超出视为超长行，直接按页大小截断
     */
    private static final int MAX_LINE_EXTEND = 8 * 1024;

    private final FileStorageDao fileStorageDao;

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    @Override
    public boolean supports(PreviewTypeEnum previewTypeEnum) {
        return Objects.equals(previewTypeEnum, PreviewTypeEnum.TEXT);
    }

    /**
     * 文本文件预览
     * <p>请求参数：offset（上一页返回的游标，优先使用）、pageNo（页码，从 1 开始）、
     * size（单页字节数，默认 64KB，取值 4KB~256KB）、charset（编码，首屏可省略由服务端探测）</p>
     *
     * @param request       请求
     * @param response      响应
     * @param sysFileEntity 文件实体
     */
    public void filePreview(HttpServletRequest request, HttpServletResponse response, SysFileEntity sysFileEntity) {
        String fileStoragePath = sysFileEntity.getFileStoragePath();
        long fileSize = sysFileEntity.getFileSize();
        int pageSize = resolvePageSize(request);
        TextPreviewVO vo = new TextPreviewVO();
        vo.setContent("");
        vo.setSize(pageSize);
        vo.setTotal(fileSize);
        vo.setPages(fileSize == 0 ? 0 : (fileSize + pageSize - 1) / pageSize);
        long offset = resolveOffset(request, pageSize, fileSize);
        vo.setOffset(offset);
        vo.setCurrent(Math.min(offset / pageSize + 1, Math.max(1, vo.getPages())));
        if (offset >= fileSize) {
            ServletUtils.writeJson(response, vo);
            return;
        }
        try {
            // 多读一段用于页尾对齐换行符，保证下一页从行首开始
            byte[] data = readRange(fileStoragePath, offset, pageSize + MAX_LINE_EXTEND);
            // 记录的文件大小与实际内容不一致（文件被截断）时直接结束，避免前端反复请求同一游标
            if (data.length == 0) {
                vo.setOffset(fileSize);
                ServletUtils.writeJson(response, vo);
                return;
            }
            int end = data.length > pageSize ? alignToLineEnd(data, pageSize) : data.length;
            byte[] page = end == data.length ? data : Arrays.copyOf(data, end);
            Charset utf8 = StandardCharsets.UTF_8;
            DecodedText decoded = decode(page, utf8);
            String content = decoded.text();
            // 首屏去掉 UTF-8/UTF-16 BOM，避免正文开头出现不可见字符
            if (offset == 0 && !content.isEmpty() && content.charAt(0) == '\uFEFF') {
                content = content.substring(1);
            }
            // 游标按实际消费的字节前进，末尾的半个多字节字符留到下一页重新解码；
            // 剩余字节不足以构成一个字符（编码与内容不符）时视为读到末尾，防止游标原地打转
            int consumed = decoded.consumed();
            long nextOffset = consumed > 0 ? offset + consumed : fileSize;
            vo.setCharset(utf8.name());
            vo.setContent(content);
            vo.setOffset(nextOffset);
            vo.setHasMore(nextOffset < fileSize);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("文本文件预览失败，fileStoragePath:{}，offset:{}", fileStoragePath, offset, e);
            throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_FAIL, e);
        }
        ServletUtils.writeJson(response, vo);
    }

    /**
     * 解析单页字节数，限制在 [{@link #MIN_PAGE_SIZE}, {@link #MAX_PAGE_SIZE}] 之间
     */
    private static int resolvePageSize(HttpServletRequest request) {
        long size = longParam(request, "size", DEFAULT_PAGE_SIZE);
        return (int) Math.max(MIN_PAGE_SIZE, Math.min(size, MAX_PAGE_SIZE));
    }

    /**
     * 解析读取起点：优先使用上一页返回的游标 offset，其次按页码 pageNo 换算
     */
    private static long resolveOffset(HttpServletRequest request, int pageSize, long fileSize) {
        long offset = longParam(request, "offset", -1);
        if (offset >= 0) {
            return Math.min(offset, fileSize);
        }
        long pageNo = Math.max(1, longParam(request, "pageNo", 1));
        // 页码超出文件范围时收敛到最后一页，避免乘法溢出
        long maxPageNo = fileSize / pageSize + 1;
        return (Math.min(pageNo, maxPageNo) - 1) * pageSize;
    }

    /**
     * 页尾对齐到换行符：先在页范围内从后向前找，找不到再向后延伸找；
     * 都找不到说明该行超长，直接按页大小截断，保证游标始终前进
     */
    private static int alignToLineEnd(byte[] data, int pageSize) {
        for (int i = pageSize - 1, from = Math.max(0, pageSize - MAX_LINE_EXTEND); i >= from; i--) {
            if (data[i] == '\n') {
                return i + 1;
            }
        }
        for (int i = pageSize; i < data.length; i++) {
            if (data[i] == '\n') {
                return i + 1;
            }
        }
        return pageSize;
    }


    /**
     * 解码一个页块
     * <p>endOfInput 传 false：输入末尾不完整的多字节字符会被保留在缓冲区中不被消费，
     * 既不会报错也不会解出乱码，{@code data.length - in.remaining()} 即本页真正消费的字节数</p>
     */
    private static DecodedText decode(byte[] data, Charset charset) throws CharacterCodingException {
        CharsetDecoder decoder = charset.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
        ByteBuffer in = ByteBuffer.wrap(data);
        CharBuffer out = CharBuffer.allocate(data.length + 16);
        CoderResult result = decoder.decode(in, out, false);
        if (result.isError()) {
            result.throwException();
        }
        out.flip();
        return new DecodedText(out.toString(), data.length - in.remaining());
    }

    /**
     * 读取指定区间，返回实际读到的字节（可能少于请求长度，表示已到文件末尾）
     */
    private byte[] readRange(String fileStoragePath, long offset, int length) throws IOException {
        try (InputStream in = fileStorageDao.downloadRange(fileStoragePath, offset, length)) {
            return in.readNBytes(length);
        }
    }

    private static long longParam(HttpServletRequest request, String name, long defaultValue) {
        String value = request.getParameter(name);
        if (!StringUtils.hasText(value)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 解码结果：文本内容 + 实际消费的字节数
     */
    private record DecodedText(String text, int consumed) {
    }
}
