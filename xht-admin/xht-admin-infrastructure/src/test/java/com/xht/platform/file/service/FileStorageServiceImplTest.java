package com.xht.platform.file.service;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.enums.StorageType;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.properties.FileUploadProperties;
import com.xht.platform.file.thumbnail.ThumbnailGenerator;
import com.xht.platform.file.thumbnail.ThumbnailProperties;
import com.xht.platform.file.utils.FileAccessChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * FileStorageServiceImpl 上传链路单元测试
 */
class FileStorageServiceImplTest {

    private FileStorageRepository fileStorageRepository;

    private SysFileDao sysFileDao;

    private SysFileConverter sysFileConverter;

    private FileUploadProperties fileUploadProperties;

    private FileAccessChecker fileAccessChecker;

    private ThumbnailGenerator thumbnailGenerator;

    private FileStorageServiceImpl fileStorageService;

    @BeforeEach
    void setUp() {
        fileStorageRepository = Mockito.mock(FileStorageRepository.class);
        // 存储类型需打桩，否则上传流程取 storageType().getValue() 空指针
        Mockito.when(fileStorageRepository.storageType()).thenReturn(StorageType.LOCAL);
        sysFileDao = Mockito.mock(SysFileDao.class);
        sysFileConverter = Mockito.mock(SysFileConverter.class);
        fileUploadProperties = new FileUploadProperties();
        fileAccessChecker = Mockito.mock(FileAccessChecker.class);
        thumbnailGenerator = Mockito.mock(ThumbnailGenerator.class);
        fileStorageService = new FileStorageServiceImpl(fileStorageRepository, sysFileDao, sysFileConverter,
                fileUploadProperties, fileAccessChecker, thumbnailGenerator, new ThumbnailProperties());
    }

    @Test
    void upload_shouldRejectDisallowedSuffix() {
        MultipartFile file = new MockMultipartFile("file", "evil.html", "text/html",
                "<h1>x</h1>".getBytes());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> fileStorageService.uploadMultipartFile(UploadBO.builder(file).build()));

        assertEquals(BusinessErrorCode.FILE_TYPE_NOT_SUPPORT.getCode(), exception.getCode());
    }

    @Test
    void upload_shouldRejectOversizeFile() {
        fileUploadProperties.setMaxSize(DataSize.ofBytes(8));
        MultipartFile file = new MockMultipartFile("file", "a.png", "image/png", pngBytes(64));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> fileStorageService.uploadMultipartFile(UploadBO.builder(file).build()));

        assertEquals(BusinessErrorCode.FILE_SIZE_EXCEED.getCode(), exception.getCode());
    }

    @Test
    void upload_shouldRejectSuffixContentMismatch() {
        // 文件名为 jpg，内容实为 PNG 魔术头
        MultipartFile file = new MockMultipartFile("file", "fake.jpg", "image/jpeg", pngBytes(64));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> fileStorageService.uploadMultipartFile(UploadBO.builder(file).build()));

        assertEquals(BusinessErrorCode.FILE_TYPE_NOT_SUPPORT.getCode(), exception.getCode());
    }

    @Test
    void upload_shouldCompensateStoredFileWhenSaveFails() {
        MultipartFile file = new MockMultipartFile("file", "a.png", "image/png", pngBytes(64));
        Mockito.doThrow(new RuntimeException("db down")).when(sysFileDao).save(any());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> fileStorageService.uploadMultipartFile(UploadBO.builder(file).build()));

        assertEquals(BusinessErrorCode.FILE_UPLOAD_FAIL.getCode(), exception.getCode());
        // 入库失败必须补偿删除已落盘文件
        verify(fileStorageRepository).remove(anyString());
    }

    @Test
    void upload_shouldFallbackUploadUserWhenNoLoginContext() {
        MultipartFile file = new MockMultipartFile("file", "a.png", "image/png", pngBytes(64));

        fileStorageService.uploadMultipartFile(UploadBO.builder(file).build());

        ArgumentCaptor<SysFileEntity> captor = ArgumentCaptor.forClass(SysFileEntity.class);
        verify(sysFileDao).save(captor.capture());
        // 无登录上下文时兜底 anonymous，不再硬编码 admin
        assertEquals("anonymous", captor.getValue().getFileUploadUser());
        // fileUsed 未传时归一化为 temp
        assertEquals("temp", captor.getValue().getFileUsed());
    }

    @Test
    void upload_shouldRejectIllegalFileUsed() {
        MultipartFile file = new MockMultipartFile("file", "a.png", "image/png", pngBytes(64));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> fileStorageService.uploadMultipartFile(
                        UploadBO.builder(file).fileUsed("unknown").build()));

        assertEquals(BusinessErrorCode.PARAM_ERROR.getCode(), exception.getCode());
    }

    /**
     * 构造带 PNG 魔术头的字节内容
     */
    private static byte[] pngBytes(int size) {
        byte[] bytes = new byte[size];
        bytes[0] = (byte) 0x89;
        bytes[1] = 0x50;
        bytes[2] = 0x4E;
        bytes[3] = 0x47;
        bytes[4] = 0x0D;
        bytes[5] = 0x0A;
        bytes[6] = 0x1A;
        bytes[7] = 0x0A;
        return bytes;
    }

}
