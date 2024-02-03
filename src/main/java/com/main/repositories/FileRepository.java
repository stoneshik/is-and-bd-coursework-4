package com.main.repositories;

import com.main.entities.file.FileInfoEntity;

import java.util.List;

public interface FileRepository {
    List<FileInfoEntity> getFilesByOrderId(Long orderId);

    boolean removeFilesByOrderId(Long orderId);

    List<FileInfoEntity> getFilesAttachedPrintOrder(Long userId);

    List<FileInfoEntity> getFilesAttachedScanOrder(Long userId);
}
