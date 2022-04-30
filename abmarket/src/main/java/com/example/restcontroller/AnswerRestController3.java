package com.example.restcontroller;

import com.example.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/answer")

public class AnswerRestController3 {

    // 문의 답변은 댓글 형태로 설정(관리자가 입력)
    
    // 토큰
    @Autowired JwtUtil jwtUtil;


}
