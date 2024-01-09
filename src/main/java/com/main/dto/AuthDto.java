package com.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthDto {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;
}
