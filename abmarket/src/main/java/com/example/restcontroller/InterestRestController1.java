package com.example.restcontroller;

import com.example.jwt.JwtUtil;
import com.example.service.InterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestRestController1 {

    @Autowired
    JwtUtil jwtuil;

    @Autowired
    InterestService1 intService1;

    // 관심사 등록하기

    // 관심사 조회하기

    // 관심사 수정하기

    // 관심사 삭제하기

    // 이미지 등록

    // 이미지 가져오기(1개)

    // 이미지 수정

    // 이미지 삭제

    // 이미지 가져오기(여러개)

}
