package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.RankEntity;
import com.example.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rank2")
public class RankRestController2 {

    @Autowired
    JwtUtil jwtUtil;

    // 등급내용 작성하기
    // 127.0.0.1:9090/ROOT/api/rank/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute RankEntity rankEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
