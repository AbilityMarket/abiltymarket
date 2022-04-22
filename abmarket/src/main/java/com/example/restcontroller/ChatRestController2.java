package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chat")
public class ChatRestController2 {

    // 토큰 가져오기
    @Autowired
    JwtUtil jwtUtil;

    // 채팅입력
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBoard3(
            @RequestHeader String token) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        // 토큰에서 이메일 추출
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);

        try {

            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }
}
