package com.main.entities.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class FileEntity {
    private Long fileId;
    private Long userId;
    private String fileName;
    private Long fileNumberPages;
    private Date fileLoadDatetime;
    private MultipartFile fileOid;
    public FileEntity(
            Long fileId,
            Long userId,
            String fileName,
            Long fileNumberPages,
            Date fileLoadDatetime,
            MultipartFile fileOid) {
        this.fileId = fileId;
        this.userId = userId;
        this.fileName = fileName;
        this.fileNumberPages = fileNumberPages;
        this.fileLoadDatetime = fileLoadDatetime;
        this.fileOid = fileOid;
    }
}