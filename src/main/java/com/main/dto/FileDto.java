package com.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.File;

@Data
public class FileDto {
    @NotNull
    @JsonProperty("file")
    private byte[] file;
    @NotNull
    @JsonProperty("typePrint")
    private String typePrint;
    @NotNull
    @JsonProperty("numberCopies")
    private Long numberCopies;
}
