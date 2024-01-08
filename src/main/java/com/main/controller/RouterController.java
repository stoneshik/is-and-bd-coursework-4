package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {
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

}
