package com.main.controller;

import com.main.dto.AuthDto;
import com.main.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    //@PostMapping("/api/auth")
    //public ResponseEntity<AuthDto> auth(@RequestBody ) {}
    @GetMapping("/aboba")
    public String aboba() {
        return userService.findByLogin("aboba").toString();
    }
}
