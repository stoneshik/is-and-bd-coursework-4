package com.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    @NotNull
    @JsonProperty("file")
    private MultipartFile file;
    @NotNull
    @JsonProperty("typePrint")
    private String typePrint;
    @NotNull
    @JsonProperty("numberCopies")
    private Long numberCopies;
}
