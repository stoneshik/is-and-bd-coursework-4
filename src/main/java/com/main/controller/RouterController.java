package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {
    /* Страницы для неавторизованного пользователя */
    @GetMapping("/")
    public String getHomeWithoutLoginPage() {
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
