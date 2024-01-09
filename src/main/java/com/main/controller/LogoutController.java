package com.main.controller;

import com.main.dto.AuthDto;
import com.main.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @PostMapping("/api/logout")
    public ResponseEntity<String> auth(HttpServletRequest httpServletRequest) {
        //authorizeHandler.newAuth(httpServletRequest, login);

        return new ResponseEntity<>("Успешный вход", HttpStatus.OK);
    }
}
