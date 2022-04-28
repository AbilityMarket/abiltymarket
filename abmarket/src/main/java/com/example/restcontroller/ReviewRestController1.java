package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.ReviewEntity;
import com.example.jwt.JwtUtil;
import com.example.service.ReviewService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/review")
public class ReviewRestController1 {

    @Autowired
    ReviewService1 revService1;

    @Autowired
    JwtUtil jwtUtil;

    // 후기 작성하기
    // 127.0.0.1:9090/ROOT/api/review/insert
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            @ModelAttribute ReviewEntity revEntity,
            @RequestHeader(name = "token") String token) {

        Map<String, Object> map = new HashMap<>();

        try {
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            MemberEntity mEntity = new MemberEntity();
            mEntity.setUid(userid);

            int ret = revService1.insertReview(revEntity);
            if (ret == 1) {
                map.put("status", 200);
            } else {
                map.put("status", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 후기 조회하기

    // 후기 수정하기

    // 후기 삭제하기
}
