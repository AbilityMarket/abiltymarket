package com.example.restcontroller;

import com.example.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/alert")

public class AlertRestController3 {

    // 토큰
    @Autowired
    JwtUtil jwtUtil;

}
