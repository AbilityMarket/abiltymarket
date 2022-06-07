package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.RankEntity;
import com.example.jwt.JwtUtil;
import com.example.service.RankService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rank2")
public class RankRestController2 {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RankService2 rankService2;

    // 회원 등급 조회하기
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectGET(
            @RequestParam(name = "uid") String uid) {

        // System.out.println(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            // String uid = jwtUtil.extractUsername(token);
            RankEntity rank = rankService2.selectRank(uid);
            if (rank != null) {
                map.put("status", 200);
                map.put("rank", rank);

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
