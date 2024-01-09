package com.main.controller;

import com.main.dto.AuthDto;
import com.main.entities.UserEntity;
import com.main.security.AuthorizeHandler;
import com.main.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorizeHandler authorizeHandler;

    @PostMapping("/api/auth")
    public ResponseEntity<String> auth(@RequestBody AuthDto authDto, HttpServletRequest httpServletRequest) {
        final String login = authDto.getLogin();
        final String password = authDto.getPassword();
        UserEntity userEntity = userService.findByLogin(login);
        if (userEntity == null) {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }
        if (!passwordEncoder.matches(password, userEntity.getUserPasswordHash())) {
            return new ResponseEntity<>("Неправильный пароль", HttpStatus.NOT_FOUND);
        }
        authorizeHandler.newAuth(httpServletRequest, login);
        return new ResponseEntity<>("Успешный вход", HttpStatus.OK);
    }
}
