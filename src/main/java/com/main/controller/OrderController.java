package com.main.controller;

import com.main.ResponseMessageWrapper;
import com.main.entities.order.OrderWithAddress;
import com.main.security.AuthorizeHandler;
import com.main.services.OrderWithAddressService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderWithAddressService orderWithAddressService;
    private final AuthorizeHandler authorizeHandler;

    @GetMapping(
            path = "/api/order/get_paid",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getPaidOrders(HttpServletRequest httpServletRequest) {
        final String login = authorizeHandler.getLoginBySessionId(httpServletRequest);
        if (login.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessageWrapper("Пользователь не авторизован"), HttpStatus.NOT_FOUND);
        }
        final List<OrderWithAddress> orders = orderWithAddressService.getPaidOrders(login);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
