package com.main.controller;

import com.main.dto.RegisterDto;
import com.main.entities.UserEntity;
import com.main.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping(
            path = "/api/open/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterDto registerDto) {
        final String email = registerDto.getEmail();
        final String login = registerDto.getLogin();
        final String password = passwordEncoder.encode(registerDto.getPassword());
        UserEntity userEntity = userService.findByLogin(login);
        if (userEntity != null) {
            return new ResponseEntity<>("Пользователь с таким логином уже существует", HttpStatus.BAD_REQUEST);
        }
        if (userService.create(email, login, password) == 0) {
            return new ResponseEntity<>("Не получилось создать пользователя", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Пользователь успешно создан", HttpStatus.CREATED);
    }
}
