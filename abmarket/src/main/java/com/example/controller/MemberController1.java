package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/member")
public class MemberController1 {

    @Autowired
    HttpSession httpSession;

    // 홈
    @GetMapping(value = "/home")
    public String homeGET() {
        return "/admin/interest/home";
    }

    // 로그인
    @GetMapping(value = "/login")
    public String loginGET() {
        return "/member/login";
    }

    // 로그아웃
    @PostMapping(value = "/logout")
    public String logoutPOST() {
        httpSession.invalidate();
        return "redirect:/";
    }
}
