package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.MemberEntity;
import com.example.entity.MeminterestEntity;
import com.example.jwt.JwtUtil;
import com.example.service.MemInterestService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/meminterest")
@RestController
public class MemInterestRestController1 {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MemInterestService1 memIntService1;

    // 관심사알람설정여부
    @RequestMapping(value = { "/insert" }, method = { RequestMethod.POST }, consumes = {
            MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @RequestHeader(name = "token") String token,
            @RequestBody MeminterestEntity memIntEntity) {

        Map<String, Object> map = new HashMap<>();

        try {
            // 토큰 사용
            String userid = jwtUtil.extractUsername(token);
            System.out.println("userid =>" + userid);

            // 회원 연결
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUid(userid);
            System.out.println("memberEntity =>" + memberEntity);

            memIntEntity.setMember(memberEntity);
            System.out.println(memIntEntity.toString());

            int ret = memIntService1.insertalert(memIntEntity);

            if (ret == 1) {
                map.put("status", 200);
                map.put("result", "알람설정");
            } else {
                map.put("status", 0);
                map.put("result", "알람미설정");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

}
