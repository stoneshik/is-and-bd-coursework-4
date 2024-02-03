package com.main.entities.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class FileWithContentEntity {
    private Long fileId;
    private Long userId;
    private String fileName;
    private Date fileLoadDatetime;
    private byte[] fileBlob;
    public FileWithContentEntity(
            Long fileId,
            Long userId,
            String fileName,
            Date fileLoadDatetime,
            byte[] fileBlob) {
        this.fileId = fileId;
        this.userId = userId;
        this.fileName = fileName;
        this.fileLoadDatetime = fileLoadDatetime;
        this.fileBlob = fileBlob;
    }
}