package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/admin")
public class AdminController2 {
    @GetMapping(value = { "/", "/home" })
    public String getAdminHome() {
        return "admin/home";
    }
}
