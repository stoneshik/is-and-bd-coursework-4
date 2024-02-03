package com.main.controller.order.get;

import com.main.ResponseMessageWrapper;
import com.main.entities.order.OrderWithAddress;
import com.main.security.AuthorizeHandler;
import com.main.services.OrderWithAddressService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetOrderController {
    private final OrderWithAddressService orderWithAddressService;
    private final AuthorizeHandler authorizeHandler;

    private ResponseEntity<Object> getOrderById(
            HttpServletRequest httpServletRequest,
            Long orderId) {
        final String login = authorizeHandler.getLoginBySessionId(httpServletRequest);
        if (login.isEmpty()) {
            return new ResponseEntity<>(
                    new ResponseMessageWrapper("Пользователь не авторизован"),
                    HttpStatus.BAD_REQUEST
            );
        }
        final OrderWithAddress order = orderWithAddressService.getOrderById(login, orderId);
        if (order == null) {
            return new ResponseEntity<>(
                    new ResponseMessageWrapper("Не получилось получить информацию о заказе"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
