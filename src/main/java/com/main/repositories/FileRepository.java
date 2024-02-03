package com.main.repositories;

import com.main.entities.file.FileInfoEntity;
import com.main.entities.file.FileWithContentEntity;

import java.util.List;

public interface FileRepository {
    FileWithContentEntity getFileById(Long userId, Long fileId);

    byte[] loadFileByOid(Long oid);

    List<FileInfoEntity> getFilesByOrderId(Long orderId);

    boolean removeFilesByOrderId(Long orderId);

    List<FileInfoEntity> getFilesAttachedPrintOrder(Long userId);

    List<FileInfoEntity> getFilesAttachedScanOrder(Long userId);
}
