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
public class GetOrderController {
    private final OrderWithAddressService orderWithAddressService;
    private final AuthorizeHandler authorizeHandler;

    @GetMapping(
            path = "/api/order/get_paid",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getPaidOrders(HttpServletRequest httpServletRequest) {
        final String login = authorizeHandler.getLoginBySessionId(httpServletRequest);
        if (login.isEmpty()) {
            return new ResponseEntity<>(
                    new ResponseMessageWrapper("Пользователь не авторизован"),
                    HttpStatus.BAD_REQUEST
            );
        }
        final List<OrderWithAddress> orders = orderWithAddressService.getPaidOrders(login);
        if (orders == null) {
            return new ResponseEntity<>(
                    new ResponseMessageWrapper("Не получилось получить информацию о заказах"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(
            path = "/api/order/get_not_paid",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> getNotPaidOrders(HttpServletRequest httpServletRequest) {
        final String login = authorizeHandler.getLoginBySessionId(httpServletRequest);
        if (login.isEmpty()) {
            return new ResponseEntity<>(
                    new ResponseMessageWrapper("Пользователь не авторизован"),
                    HttpStatus.BAD_REQUEST
            );
        }
        final List<OrderWithAddress> orders = orderWithAddressService.getNotPaidOrders(login);
        if (orders == null) {
            return new ResponseEntity<>(
                    new ResponseMessageWrapper("Не получилось получить информацию о заказах"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}