package com.main.controller;

import com.main.dto.AuthDto;
import com.main.entities.UserEntity;
import com.main.security.AuthorizeHandler;
import com.main.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorizeHandler authorizeHandler;

    @PostMapping(
            path = "/api/open/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> auth(
            @Valid @RequestBody AuthDto authDto,
            HttpServletRequest httpServletRequest) {
        final String login = authDto.getLogin();
        final String password = authDto.getPassword();
        UserEntity userEntity = userService.findByLogin(login);
        if (userEntity == null) {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }
        if (!passwordEncoder.matches(password, userEntity.getUserPasswordHash())) {
            return new ResponseEntity<>("Неправильный пароль", HttpStatus.BAD_REQUEST);
        }
        if (!authorizeHandler.newAuth(httpServletRequest, login)) {
            return new ResponseEntity<>("Уже был произведен вход", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Успешный вход", HttpStatus.OK);
    }
}
