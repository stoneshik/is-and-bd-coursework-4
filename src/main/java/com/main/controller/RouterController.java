package com.main.controller;

import com.main.ResponseMessageWrapper;
import com.main.security.AuthorizeHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RouterController {
    private final AuthorizeHandler authorizeHandler;
    /* Страницы для неавторизованного пользователя */
    @GetMapping("/")
    public String getHomeWithoutLoginPage(HttpServletRequest httpServletRequest) {
        final String login = authorizeHandler.getLoginBySessionId(httpServletRequest);
        if (!login.isEmpty()) {
            return "redirect:/main";
        }
        return "index";
    }
    @GetMapping("/login")
    public String getLoginPage() {
        return "index";
    }
    @GetMapping("/register")
    public String getRegisterPage() {
        return "index";
    }
    @GetMapping("/map_without_login")
    public String getMapWithoutLoginPage() {
        return "index";
    }
    /* Страницы для авторизованного пользователя */
    @GetMapping("/main")
    public String getHomePage() {
        return "index";
    }
    @GetMapping("/map")
    public String getMapPage() {
        return "index";
    }
    @GetMapping("/new_order_print")
    public String getNewOrderPrintPage() {
        return "index";
    }
    @GetMapping("/new_order_scan")
    public String getNewOrderScanPage() {
        return "index";
    }
    @GetMapping("/cart")
    public String getCartPage() {
        return "index";
    }
    @GetMapping("/files")
    public String getFilesPage() {
        return "index";
    }

}
